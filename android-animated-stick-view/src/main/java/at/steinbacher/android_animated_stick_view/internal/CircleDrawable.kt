package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import at.steinbacher.android_animated_stick_view.Circle
import at.steinbacher.android_animated_stick_view.util.MathUtil

class CircleDrawable(context : Context,
                     val sourceCircle : Circle,
                     var translatedMiddlePointF: PointF,
                     val translatedRadius: Float,
                     horizontalLinesCount: Int, verticalLinesCount: Int,
                     width: Float, height: Float, tag: String
    ): SimpleDrawable(context, horizontalLinesCount, verticalLinesCount, width, height, tag) {


    override fun distanceTo(x: Float, y: Float): Float {
        val distance = MathUtil.distanceBetweenPoints(x,y, translatedMiddlePointF.x, translatedMiddlePointF.y)
        return distance-translatedRadius
    }

    override fun move(moveX: Float, moveY: Float) {
        translatedMiddlePointF = PointF(translatedMiddlePointF.x-moveX, translatedMiddlePointF.y-moveY)
    }

    override fun rotate(angle: Float) {}


    override fun draw(canvas: Canvas) {
        canvas.drawCircle(translatedMiddlePointF.x, translatedMiddlePointF.y, translatedRadius, sourceCircle.paint)

        if(highlighted) {
            val distance = 10
            val left = translatedMiddlePointF.x - translatedRadius - distance
            val top = translatedMiddlePointF.y - translatedRadius - distance
            val right = translatedMiddlePointF.x + translatedRadius + distance
            val bottom = translatedMiddlePointF.y + translatedRadius + distance
            canvas.drawRect(left, top, right, bottom, highlightPaint)
        }
    }

}