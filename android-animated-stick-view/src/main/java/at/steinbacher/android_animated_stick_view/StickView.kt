package at.steinbacher.android_animated_stick_view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import at.steinbacher.android_animated_stick_view.internal.GridDrawable
import at.steinbacher.android_animated_stick_view.internal.SceneDrawable
import at.steinbacher.android_animated_stick_view.internal.StickDrawable
import at.steinbacher.android_animated_stick_view.internal.StickDrawableTypeEvaluator

class StickView : View, ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        applyAttributes(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        applyAttributes(attrs)
    }

    private var gridDrawable: GridDrawable? = null
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
    private var showGrid = false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas == null) return

        //init the view
        if (!initDone) init()

        //draw grid if needed
        gridDrawable?.draw(canvas)

        //add sticks to the current scene if needed
        for(stick in receivedScenes[currentSceneIndex].sticks) {
            if(!sceneDrawable.contains(stick.tag)) {
                sceneDrawable.addStick(stick)
            }
        }

        //draw current scene
        sceneDrawable.draw(canvas)

        //start animation if needed
        if(startAnimation && receivedScenes.size > 1) {
            runAnimation()
            startAnimation = false
        }
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
     * Start looping through the provided scenes
     * @see #setScenes(scenes : List<Scene>)
     */
    fun startAnimation() {
        startAnimation = true
        invalidate()
    }

    /**
     * Draw a grid on the view background. Useful for debugging the provided scenes
     * @param show True if grid should be visible, False if not
     */
    fun showGrid(show: Boolean) {
        showGrid = show

        if(initDone) {
            gridDrawable = if (show) {
                GridDrawable(context, horizontalLinesCount, verticalLinesCount, width.toFloat(), height.toFloat())
            } else {
                null
            }
        }

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

        initDone = false
        startAnimation = false
        invalidate()
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
        return verticalLinesCount
    }

    /**
     * Returns the currently set horizontal grid lines count
     */
    fun getHorizontalLinesCount(): Int {
        return horizontalLinesCount
    }

    /**
     * Sets the scene to be shown
     * @param index index of the target scene
     */
    fun showScene(index: Int) {
        if(index in receivedScenes.indices) {
            currentSceneIndex = index

            if(initDone) {
                sceneDrawable.clearSticks()
                invalidate()
            }
        } else {
            throw IndexOutOfBoundsException()
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

            if(typedArray.hasValue(R.styleable.StickView_showGrid)) {
                showGrid = typedArray.getBoolean(R.styleable.StickView_verticalLinesCount, false)
            }

            if(typedArray.hasValue(R.styleable.StickView_animationDuration)) {
                animationDuration = typedArray.getInt(R.styleable.StickView_animationDuration,-1).toLong()
            }

            if(typedArray.hasValue(R.styleable.StickView_animationRepeat)) {
                animationRepeatMode = typedArray.getBoolean(R.styleable.StickView_animationRepeat,false)
            }
        }
    }

    private fun init() {
        //show grid?
        if(showGrid) {
            gridDrawable = GridDrawable(
                context, horizontalLinesCount, verticalLinesCount, width.toFloat(), height.toFloat()
            )
        }

        sceneDrawable = SceneDrawable(context, horizontalLinesCount, verticalLinesCount,
            width.toFloat(), height.toFloat())

        initDone = true
    }

    private fun runAnimation() {
        val nextSceneDrawable = SceneDrawable(context,
            sceneDrawable.horizontalLinesCount, sceneDrawable.verticalLinesCount,
            sceneDrawable.width, sceneDrawable.height).also { it ->
                for(stick in receivedScenes[currentSceneIndex + 1].sticks) {
                    it.addStick(stick)
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
                sceneDrawable.clearSticks()
                currentSceneIndex = 0
                for (stick in receivedScenes[currentSceneIndex].sticks) {
                    if (!sceneDrawable.contains(stick.tag)) {
                        sceneDrawable.addStick(stick)
                    }
                }
            }

            val nextDrawnScene = SceneDrawable(
                context,
                sceneDrawable.horizontalLinesCount, sceneDrawable.verticalLinesCount,
                sceneDrawable.width, sceneDrawable.height
            ).also { it ->
                for (stick in receivedScenes[currentSceneIndex + 1].sticks) {
                    it.addStick(stick)
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
            val animatedSticks = animation.animatedValue as Array<StickDrawable>
            for(element in animatedSticks) {
                sceneDrawable.updateStickDrawable(element)
            }

            invalidate()
        }
    }

    private fun createValueAnimator(sourceSceneDrawable: SceneDrawable, targetSceneDrawable: SceneDrawable): ValueAnimator {
        val targetSceneSticks: List<StickDrawable> = targetSceneDrawable.getStickDrawables()
        val currentDrawnSticks: Array<StickDrawable?> = arrayOfNulls<StickDrawable>(targetSceneSticks.size)
        val targetSticks: Array<StickDrawable?> = arrayOfNulls<StickDrawable>(targetSceneSticks.size)

        for (i in targetSceneSticks.indices) {
            val stick: StickDrawable = targetSceneSticks[i]
            if (sourceSceneDrawable.contains(stick.tag)) {
                currentDrawnSticks[i] = sourceSceneDrawable.getStickDrawable(stick.tag)
                targetSticks[i] = stick
            }
        }

        val valueAnimator = ValueAnimator.ofObject(StickDrawableTypeEvaluator(), currentDrawnSticks, targetSticks)
        valueAnimator.duration = animationDuration
        valueAnimator.interpolator = animationInterpolator

        return valueAnimator
    }
}