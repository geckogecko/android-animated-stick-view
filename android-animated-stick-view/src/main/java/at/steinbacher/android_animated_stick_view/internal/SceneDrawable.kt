package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import at.steinbacher.android_animated_stick_view.*

private const val TAG = "SceneDrawable"

class SceneDrawable(context: Context,
                    horizontalLinesCount: Int,
                    verticalLinesCount: Int,
                    width: Float,
                    height: Float,
                    tag: String) : SimpleDrawable(context, horizontalLinesCount, verticalLinesCount,
    width, height, tag) {

    private val simpleDrawables : MutableList<SimpleDrawable> = ArrayList()

    fun addSimpleDrawable(simple: Simple): Boolean {
        val factory = getSimpleDrawableFactory()
        val simpleDrawable = factory.createSimpleDrawable(simple)
        return if(simpleDrawable != null) {
            simpleDrawables.add(simpleDrawable)
            true
        } else {
            false
        }
    }

    fun getCurrentScene(): Scene {
        val factory = getSimpleFactory()

        val scene = Scene()
        simpleDrawables.forEach {
            factory.createSimple(it)?.let { simple ->
                scene.simples.add(simple)
            }
        }

        return scene
    }

    override fun distanceTo(x: Float, y: Float): Float {
        return Float.MAX_VALUE
    }

    override fun move(moveX: Float, moveY: Float) {
    }

    override fun rotate(angle: Float) {
    }

    override fun scaleHeight(moveY: Float) {
    }

    override fun scaleWidth(moveX: Float) {
    }

    override fun draw(canvas : Canvas) {
        for(simpleDrawables in simpleDrawables) {
            simpleDrawables.draw(canvas)
        }
    }

    fun contains(tag : String) : Boolean {
        for(simpleDrawable in simpleDrawables) {
            if(simpleDrawable.tag == tag) return true
        }

        return false
    }

    fun getSimpleDrawable(tag : String) : SimpleDrawable? {
        for(simpleDrawable in simpleDrawables) {
            if(simpleDrawable.tag == tag) return simpleDrawable
        }

        return null
    }

    fun updateSimpleDrawable(updateSimpleDrawable: SimpleDrawable) {
        for(i in 0 until simpleDrawables.size) {
            if(simpleDrawables[i].tag == updateSimpleDrawable.tag) {
                this.simpleDrawables[i] = updateSimpleDrawable
            }
        }
    }

    fun getSimpleDrawables() : MutableList<SimpleDrawable> {
        return simpleDrawables
    }

    fun clearSimpleDrawables() {
        simpleDrawables.clear()
    }

    fun getSimpleDrawableFactory() = SimpleDrawableFactory(context,
        horizontalLinesCount, verticalLinesCount,
        width, height,
        cellWidth, cellHeight)

    fun getSimpleFactory() = SimpleFactory(context,
        horizontalLinesCount, verticalLinesCount,
        width, height,
        cellWidth, cellHeight)

    fun getClickedDrawable(x: Float, y: Float): SimpleDrawable? {
        var currentMinDistance: Float = Float.MAX_VALUE
        var currentClicked: SimpleDrawable? = null
        simpleDrawables.forEach {
            val distance = it.distanceTo(x, y)
            if(distance < currentMinDistance) {
                currentMinDistance = distance
                currentClicked = it
            }
        }

        return if(currentClicked != null && currentMinDistance < 50) {
            Log.i(TAG, "${currentClicked!!.tag} got clicked!")
            currentClicked
        } else {
            Log.i(TAG, "nothing got clicked! $currentMinDistance")
            null
        }
    }

    fun highlightDrawable(simpleDrawable: SimpleDrawable, highlighted: Boolean) {
        simpleDrawable.highlighted = highlighted
    }

    fun moveDrawable(simpleDrawable: SimpleDrawable, moveX: Float, moveY: Float) {
        simpleDrawable.move(moveX, moveY)
    }

    fun rotateDrawable(simpleDrawable: SimpleDrawable, angle: Float) {
        simpleDrawable.rotate(Math.toRadians(angle.toDouble()).toFloat())
    }

    fun scaleDrawableHeight(simpleDrawable: SimpleDrawable, moveY: Float) {
        simpleDrawable.scaleHeight(moveY)
    }

    fun scaleDrawableWidth(simpleDrawable: SimpleDrawable, moveX: Float) {
        simpleDrawable.scaleWidth(moveX)
    }
}