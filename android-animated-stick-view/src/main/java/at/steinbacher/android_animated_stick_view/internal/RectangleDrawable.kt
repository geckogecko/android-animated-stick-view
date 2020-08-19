package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import at.steinbacher.android_animated_stick_view.Line
import at.steinbacher.android_animated_stick_view.Rectangle
import at.steinbacher.android_animated_stick_view.util.MathUtil

class RectangleDrawable(context : Context,
                        val sourceRectangle: Rectangle,
                        val translatedLeftTop: PointF,
                        val translatedRightBottom: PointF,
                        horizontalLinesCount: Int, verticalLinesCount: Int,
                        width: Float, height: Float, tag: String
): SimpleDrawable(context, horizontalLinesCount, verticalLinesCount, width, height, tag) {

    override fun distanceTo(x: Float, y: Float): Float {
        //is inside?
        return if(x >= translatedLeftTop.x &&  x <= translatedRightBottom.x
            && y >= translatedLeftTop.y && y <= translatedRightBottom.y) {
            0f
        } else {
            Float.MAX_VALUE
        }
    }

    override fun move(moveX: Float, moveY: Float) {
        translatedLeftTop.x -= moveX
        translatedLeftTop.y -= moveY

        translatedRightBottom.x -= moveX
        translatedRightBottom.y -= moveY
    }

    override fun rotate(angle: Float) {
        //TODO
    }

    override fun scaleHeight(moveY: Float) {
        translatedRightBottom.y -= moveY
    }

    override fun scaleWidth(moveX: Float) {
        translatedRightBottom.x -= moveX
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

        if(highlighted) {
            val distance = 10
            val left = translatedLeftTop.x  - distance
            val top = translatedLeftTop.y - distance
            val right = translatedRightBottom.x + distance
            val bottom = translatedRightBottom.y + distance
            canvas.drawRect(left, top, right, bottom, highlightPaint)
        }
    }
}