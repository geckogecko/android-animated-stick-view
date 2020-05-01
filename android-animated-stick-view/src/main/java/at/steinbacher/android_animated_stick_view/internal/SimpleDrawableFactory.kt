package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.PointF
import at.steinbacher.android_animated_stick_view.Circle
import at.steinbacher.android_animated_stick_view.Grid
import at.steinbacher.android_animated_stick_view.Line
import at.steinbacher.android_animated_stick_view.Simple

class SimpleDrawableFactory(val context: Context,
                            val horizontalLinesCount: Int,
                            val verticalLinesCount: Int,
                            val width: Float,
                            val height: Float,
                            val cellWidth: Float,
                            val cellHeight: Float) {

    fun createSimpleDrawable(simple: Simple): SimpleDrawable? {
        when(simple) {
            is Line -> {
                return createLineDrawable(simple)
            }
            is Grid -> {
                return createGridDrawable(simple)
            }
            is Circle -> {
                return createCircleDrawable(simple)
            }
        }

        return null
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