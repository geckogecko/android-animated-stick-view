package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import at.steinbacher.android_animated_stick_view.Circle
import at.steinbacher.android_animated_stick_view.Grid
import at.steinbacher.android_animated_stick_view.Simple
import at.steinbacher.android_animated_stick_view.Line

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
}