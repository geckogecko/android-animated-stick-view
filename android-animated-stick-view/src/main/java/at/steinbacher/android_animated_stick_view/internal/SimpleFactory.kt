package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.PointF
import at.steinbacher.android_animated_stick_view.*

class SimpleFactory(val context: Context,
                    val horizontalLinesCount: Int,
                    val verticalLinesCount: Int,
                    val width: Float,
                    val height: Float,
                    val cellWidth: Float,
                    val cellHeight: Float) {

    fun createSimple(simpleDrawable: SimpleDrawable): Simple? {
        when(simpleDrawable) {
            is LineDrawable -> {
                return createLine(simpleDrawable)
            }
            is GridDrawable -> {
                return simpleDrawable.sourceGrid
            }
            is CircleDrawable -> {
                return createCircle(simpleDrawable)
            }
            is RectangleDrawable -> {
                return simpleDrawable.sourceRectangle
            }
        }

        return null
    }

    private fun createCircle(circleDrawable: CircleDrawable) =  Circle(
        translatePoint(circleDrawable.translatedMiddlePointF),
        translateFloat(circleDrawable.translatedRadius, cellWidth),
        circleDrawable.sourceCircle.paint,
        circleDrawable.tag
    )

    private fun createLine(lineDrawable: LineDrawable) =  Line(
        translatePoint(lineDrawable.translatedStartPoint),
        translatePoint(lineDrawable.translatedEndPoint),
        lineDrawable.sourceLine.paint,
        lineDrawable.tag
    )

    private fun translatePoint(point: PointF) = PointF(
        translateFloat(point.x, cellWidth),
        translateFloat(point.y, cellHeight)
    )

    private fun translateFloat(float: Float, translation: Float) = float / translation
}