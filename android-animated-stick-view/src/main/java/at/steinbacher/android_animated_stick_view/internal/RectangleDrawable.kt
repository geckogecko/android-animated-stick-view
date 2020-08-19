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

    override fun distanceTo(x: Float, y: Float): Float {
        return Float.MAX_VALUE
    }

    override fun move(x: Float, y: Float) {
        TODO("Not yet implemented")
    }

    override fun rotate(angle: Float) {
    }

    override fun draw(canvas: Canvas) {
        val vectorDrawable = sourceRectangle.getVectorDrawable()
        if(vectorDrawable != null) {
            vectorDrawable.setBounds(translatedLeftTop.x.toInt(), translatedLeftTop.y.toInt(),
                translatedRightBottom.x.toInt(), translatedRightBottom.y.toInt())
            vectorDrawable.draw(canvas)
        } else {
            canvas.drawRect(
                translatedLeftTop.x, translatedLeftTop.y,
                translatedRightBottom.x, translatedRightBottom.y,
                sourceRectangle.paint
            )
        }
    }
}