package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import at.steinbacher.android_animated_stick_view.Circle
import at.steinbacher.android_animated_stick_view.Grid
import at.steinbacher.android_animated_stick_view.Simple
import at.steinbacher.android_animated_stick_view.Stick

class SceneDrawable(context: Context,
                    horizontalLinesCount: Int,
                    verticalLinesCount: Int,
                    width: Float,
                    height: Float,
                    tag: String) : SimpleDrawable(context, horizontalLinesCount, verticalLinesCount,
    width, height, tag) {

    private val simpleDrawables : MutableList<SimpleDrawable> = ArrayList()

    fun addSimpleDrawable(simple: Simple) {
        when(simple) {
            is Stick -> {
                val translatedStartPoint = PointF(
                    simple.startPointF.x * cellWidth,
                    simple.startPointF.y * cellHeight
                )

                val translatedEndPoint = PointF(
                    simple.endPointF.x * cellWidth,
                    simple.endPointF.y * cellHeight
                )

                StickDrawable(context, simple, translatedStartPoint, translatedEndPoint,
                    verticalLinesCount, horizontalLinesCount, width, height, simple.tag
                ).also {
                    simpleDrawables.add(it)
                }
            }
            is Grid -> {
                GridDrawable(context, horizontalLinesCount, verticalLinesCount, width, height, simple.tag).also {
                    simpleDrawables.add(it)
                }
            }
            is Circle -> {
                val translatedMiddlePointF = PointF(
                    simple.middlePointF.x * cellWidth,
                    simple.middlePointF.y * cellHeight
                )

                val translatedRadius = simple.radius * cellWidth
                CircleDrawable(context, simple, translatedMiddlePointF, translatedRadius,
                    horizontalLinesCount, verticalLinesCount, width, height, simple.tag).also {
                    simpleDrawables.add(it)
                }
            }
        }
    }

    override fun draw(canvas : Canvas) {
        for(stickDrawable in simpleDrawables) {
            stickDrawable.draw(canvas)
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
}