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

    fun addSimpleDrawable(simple: Simple) {
        when(simple) {
            is Line -> {
                simpleDrawables.add(createLineDrawable(simple))
            }
            is Grid -> {
                simpleDrawables.add(createGridDrawable(simple))
            }
            is Circle -> {
                simpleDrawables.add(createCircleDrawable(simple))
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

    private fun createGridDrawable(grid: Grid) = GridDrawable(
        context,
        grid,
        horizontalLinesCount,
        verticalLinesCount,
        width,
        height,
        grid.tag)

    private fun createLineDrawable(line: Line) = LineDrawable(
        context,
        line,
        translatePoint(line.startPoint),
        translatePoint(line.endPoint),
        verticalLinesCount,
        horizontalLinesCount,
        width,
        height,
        line.tag)

    private fun createCircleDrawable(circle: Circle) =  CircleDrawable(
        context,
        circle,
        translatePoint(circle.middlePointF),
        translateFloat(circle.radius, cellWidth),
        horizontalLinesCount,
        verticalLinesCount,
        width,
        height,
        circle.tag)

    private fun translatePoint(point: PointF) = PointF(
        translateFloat(point.x, cellWidth),
        translateFloat(point.y, cellHeight))

    private fun translateFloat(float: Float, translation: Float) = float * translation
}