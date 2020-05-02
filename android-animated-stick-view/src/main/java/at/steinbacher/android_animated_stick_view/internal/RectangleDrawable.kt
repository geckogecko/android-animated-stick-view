package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import at.steinbacher.android_animated_stick_view.Line
import at.steinbacher.android_animated_stick_view.Rectangle

class RectangleDrawable(context : Context,
                        val sourceRectangle: Rectangle,
                        val translatedLeftTop: PointF,
                        val translatedRightBottom: PointF,
                        horizontalLinesCount: Int, verticalLinesCount: Int,
                        width: Float, height: Float, tag: String
): SimpleDrawable(context, horizontalLinesCount, verticalLinesCount, width, height, tag) {

    override fun draw(canvas: Canvas) {
        canvas.drawRect(translatedLeftTop.x, translatedLeftTop.y,
            translatedRightBottom.x, translatedRightBottom.y,
            sourceRectangle.paint)
    }
}