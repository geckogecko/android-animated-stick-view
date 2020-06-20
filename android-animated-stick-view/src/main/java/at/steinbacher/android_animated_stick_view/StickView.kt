package at.steinbacher.android_animated_stick_view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.LinearLayout
import at.steinbacher.android_animated_stick_view.internal.*
import kotlinx.android.synthetic.main.layout_stick_view.view.*
import kotlin.math.floor

class StickView : LinearLayout, ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener, View.OnTouchListener {
    constructor(context: Context) : this(context, null) {
        setWillNotDraw(false)
        inflate()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        setWillNotDraw(false)
        applyAttributes(attrs)
        inflate()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setWillNotDraw(false)
        applyAttributes(attrs)
        inflate()
    }

    private lateinit var sceneDrawable: SceneDrawable
    private var currentSceneIndex = 0

    //settable by user
    private var receivedScenes: List<Scene> = ArrayList()
    private var horizontalLinesCount: Int = 8
    private var verticalLinesCount: Int = 8
    private var animationDuration = 2000L //ms
    private var animationInterpolator: Interpolator = AccelerateDecelerateInterpolator()

    private var initDone = false
    private var startAnimation = false
    private var animationRepeatMode = false
    private var dynamicHorizontalLinesCount = false
    private var dynamicVerticalLinesCount = false

    private var currentlyDraggedDrawable: SimpleDrawable? = null

    private var lastMotionX: Float? = null
    private var lastMotionY: Float? = null

    override fun onDraw(canvas: Canvas?) {
        if(canvas == null) return

        //init the view
        if (!initDone) init()

        //add simpleDrawables to the current scene if needed
        for(simpleDrawable in receivedScenes[currentSceneIndex].simples) {
            if(!sceneDrawable.contains(simpleDrawable.tag)) {
                sceneDrawable.addSimpleDrawable(simpleDrawable)
            }
        }

        //draw current scene
        sceneDrawable.draw(canvas)

        //start animation if needed
        if(startAnimation && receivedScenes.size > 1) {
            runAnimation()
            startAnimation = false
        }

        super.onDraw(canvas)
    }

    /**
     * @param scenes to loop through
     * Initially the first scene is shown. After startAnimation() is called StickView starts looping
     * through the provided scenes.
     * @see #startAnimation()
     */
    fun setScenes(scenes: List<Scene>) {
        receivedScenes = scenes
    }

    /**
     * @param sceneCollection to loop through
     * Initially the first scene is shown. After startAnimation() is called StickView starts looping
     * through the provided scenes.
     * @see #startAnimation()
     */
    fun setSceneCollection(sceneCollection: SceneCollection) {
        receivedScenes = sceneCollection.scenes
    }

    /**
     * Start looping through the provided scenes
     * @see #setScenes(scenes : List<Scene>)
     */
    fun startAnimation() {
        startAnimation = true
        invalidate()
    }

    /**
     * Sets the grid line count in horizontal and vertical direction
     * @param horizontalLinesCount Number of grid lines in horizontal direction
     * @param verticalLinesCount Number of grid lines in vertical direction
     */
    fun setGrid(horizontalLinesCount: Int, verticalLinesCount: Int) {
        this.horizontalLinesCount = horizontalLinesCount
        this.verticalLinesCount = verticalLinesCount
    }

    /**
     * Sets the grid line count in horizontal direction
     * @param horizontalLinesCount Number of grid lines in horizontal direction
     */
    fun setHorizontalLinesCount(horizontalLinesCount: Int) {
        this.horizontalLinesCount = horizontalLinesCount
    }

    /**
     * Sets the grid line count in horizontal direction
     * @param verticalLinesCount Number of grid lines in horizontal direction
     */
    fun setVerticalLinesCount(verticalLinesCount: Int) {
        this.verticalLinesCount = verticalLinesCount
    }

    /**
     * Sets the length of the animation between two scenes. Default is 2000ms
     * @param duration The length of the animation in milliseconds.
     */
    fun setAnimationDuration(duration: Long) {
        animationDuration = duration
    }

    /**
     * Set the interpolator to be used with the animation between two scenes. Default is AccelerateDecelerateInterpolator()
     * @param interpolator the interpolator to be used.
     */
    fun setAnimationInterpolator(interpolator: Interpolator) {
        animationInterpolator = interpolator
    }

    /**
     * Sets the repeat mode of the animation
     * @param enabled True when animation should repeat after finishing, False if not
     */
    fun setAnimationRepeatMode(enabled: Boolean) {
        animationRepeatMode = enabled
    }

    /**
     * Returns the currently set vertical grid lines count
     */
    fun getVerticalLinesCount(): Int {
        return if(dynamicVerticalLinesCount) {
            if(initDone) {
                verticalLinesCount
            } else {
                -1
            }
        } else {
            verticalLinesCount
        }
    }

    /**
     * Returns the currently set horizontal grid lines count
     */
    fun getHorizontalLinesCount(): Int {
        return if(dynamicHorizontalLinesCount) {
            if(initDone) {
                horizontalLinesCount
            } else {
                -1
            }
        } else {
            horizontalLinesCount
        }
    }

    /**
     * Sets the scene to be shown
     * @param index index of the target scene
     */
    fun showScene(index: Int) {
        if(index in receivedScenes.indices) {
            currentSceneIndex = index

            if(initDone) {
                sceneDrawable.clearSimpleDrawables()
                invalidate()
            }
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    /**
     * Enable dynamic calculation of the horizontal lines count
     * @param enabled True if the horizontal lines count should be calculated based on the cell width,
     * False if not. Can't be enabled together with enableDynamicVerticalLinesCount()
     */
    fun enableDynamicHorizontalLinesCount(enabled: Boolean) {
        if(!dynamicVerticalLinesCount) {
            dynamicHorizontalLinesCount = enabled
        }
    }

    /**
     * Enable dynamic calculation of the vertical lines count
     * @param enabled True if the vertical lines count should be calculated based on the cell height,
     * False if not. Can't be enabled together with enableDynamicHorizontalLinesCount()
     */
    fun enableDynamicVerticalLinesCount(enabled: Boolean) {
        if(!dynamicHorizontalLinesCount) {
            dynamicVerticalLinesCount = enabled
        }
    }

    /**
     * TODO
     */
    fun getCurrentScene(): Scene {
        return sceneDrawable.getCurrentScene()
    }

    private fun inflate() {
        View.inflate(context, R.layout.layout_stick_view, this)

        button_edit_save.setOnClickListener {
            currentlyDraggedDrawable?.let {
                sceneDrawable.highlightDrawable(it, false)
                currentlyDraggedDrawable = null
                invalidate()

                nav_edit.visibility = View.GONE
            }
        }
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        if(attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickView)

            if(typedArray.hasValue(R.styleable.StickView_horizontalLinesCount)) {
                horizontalLinesCount = typedArray.getInt(R.styleable.StickView_horizontalLinesCount, -1)
            }

            if(typedArray.hasValue(R.styleable.StickView_verticalLinesCount)) {
                verticalLinesCount = typedArray.getInt(R.styleable.StickView_verticalLinesCount, -1)
            }

            if(typedArray.hasValue(R.styleable.StickView_animationDuration)) {
                animationDuration = typedArray.getInt(R.styleable.StickView_animationDuration,-1).toLong()
            }

            if(typedArray.hasValue(R.styleable.StickView_animationRepeat)) {
                animationRepeatMode = typedArray.getBoolean(R.styleable.StickView_animationRepeat,false)
            }

            if(typedArray.hasValue(R.styleable.StickView_dynamicVerticalLinesCount)) {
                dynamicVerticalLinesCount = typedArray.getBoolean(R.styleable.StickView_dynamicVerticalLinesCount,false)
            }

            if(typedArray.hasValue(R.styleable.StickView_horizontalLinesCount) && !dynamicVerticalLinesCount) {
                dynamicHorizontalLinesCount = typedArray.getBoolean(R.styleable.StickView_horizontalLinesCount,false)
            }

            typedArray.recycle()
        }
    }

    private fun init() {
        //dynamic horizontal or vertical?
        if(dynamicHorizontalLinesCount) {
            val cellWidth = width / verticalLinesCount
            horizontalLinesCount = floor((height / cellWidth).toDouble()).toInt()
        }

        if(dynamicVerticalLinesCount) {
            val cellHeight = height / horizontalLinesCount
            verticalLinesCount = floor((width / cellHeight).toDouble()).toInt()
        }

        sceneDrawable = SceneDrawable(context, horizontalLinesCount, verticalLinesCount,
            width.toFloat(), height.toFloat(), "scene_drawable")

        initDone = true

        setOnTouchListener(this)
    }

    private fun runAnimation() {
        val nextSceneDrawable = SceneDrawable(context,
            sceneDrawable.horizontalLinesCount, sceneDrawable.verticalLinesCount,
            sceneDrawable.width, sceneDrawable.height, "next_scene_drawable").also { it ->
                for(simpleDrawable in receivedScenes[currentSceneIndex + 1].simples) {
                    it.addSimpleDrawable(simpleDrawable)
                }
        }

        val valueAnimator = createValueAnimator(sceneDrawable, nextSceneDrawable)
        valueAnimator.addListener(this)
        valueAnimator.addUpdateListener(this)
        valueAnimator.start()
    }

    override fun onAnimationRepeat(animation: Animator?) {
        // we don't repeat the animation
    }

    override fun onAnimationEnd(animation: Animator?) {
        if(animationRepeatMode) {
            currentSceneIndex++
            if (currentSceneIndex >= receivedScenes.size - 1) {
                sceneDrawable.clearSimpleDrawables()
                currentSceneIndex = 0
                for (simpleDrawable in receivedScenes[currentSceneIndex].simples) {
                    if (!sceneDrawable.contains(simpleDrawable.tag)) {
                        sceneDrawable.addSimpleDrawable(simpleDrawable)
                    }
                }
            }

            val nextDrawnScene = SceneDrawable(
                context,
                sceneDrawable.horizontalLinesCount, sceneDrawable.verticalLinesCount,
                sceneDrawable.width, sceneDrawable.height, "next_scene_drawable"
            ).also { it ->
                for (simpleDrawable in receivedScenes[currentSceneIndex + 1].simples) {
                    it.addSimpleDrawable(simpleDrawable)
                }
            }

            val valueAnimator = createValueAnimator(sceneDrawable, nextDrawnScene)
            valueAnimator.addListener(this)
            valueAnimator.addUpdateListener(this)
            valueAnimator.start()
        }
    }

    override fun onAnimationCancel(animation: Animator?) {
        //nothing to do
    }

    override fun onAnimationStart(animation: Animator?) {
        //nothing to do
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        if(animation != null) {
            val animatedSticks = animation.animatedValue as Array<*>
            for(element in animatedSticks) {
                sceneDrawable.updateSimpleDrawable(element as SimpleDrawable)
            }

            invalidate()
        }
    }

    private fun createValueAnimator(sourceSceneDrawable: SceneDrawable, targetSceneDrawable: SceneDrawable): ValueAnimator {
        val targetSceneSDS: MutableList<SimpleDrawable> = targetSceneDrawable.getSimpleDrawables()
        val currentDrawnSDS: Array<SimpleDrawable?> = arrayOfNulls<SimpleDrawable>(targetSceneSDS.size)
        val targetSDS: Array<SimpleDrawable?> = arrayOfNulls<SimpleDrawable>(targetSceneSDS.size)

        for (i in targetSceneSDS.indices) {
            val simpleDrawable: SimpleDrawable = targetSceneSDS[i]
            if (sourceSceneDrawable.contains(simpleDrawable.tag)) {
                currentDrawnSDS[i] = sourceSceneDrawable.getSimpleDrawable(simpleDrawable.tag)
                targetSDS[i] = simpleDrawable
            }
        }

        val valueAnimator = ValueAnimator.ofObject(SimpleDrawableTypeEvaluator(), currentDrawnSDS, targetSDS)
        valueAnimator.duration = animationDuration
        valueAnimator.interpolator = animationInterpolator

        return valueAnimator
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event != null) {
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if(currentlyDraggedDrawable == null) {
                        currentlyDraggedDrawable = sceneDrawable.getClickedDrawable(event.x, event.y)
                        currentlyDraggedDrawable?.let {
                            nav_edit.visibility = View.VISIBLE
                            sceneDrawable.highlightDrawable(it, true)
                            invalidate()
                        }
                    }
                }
                MotionEvent.ACTION_MOVE -> currentlyDraggedDrawable?.let {
                    if(lastMotionX != null && lastMotionY != null) {
                        sceneDrawable.moveDrawable(it, lastMotionX!!-event.x, lastMotionY!!-event.y)
                        invalidate()
                    }

                    lastMotionX = event.x
                    lastMotionY = event.y
                }

                MotionEvent.ACTION_UP -> {
                    lastMotionX = null
                    lastMotionY = null
                }
            }
        }
        return true
    }
}