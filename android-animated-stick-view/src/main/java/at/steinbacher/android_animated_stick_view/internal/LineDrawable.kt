package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import androidx.core.content.ContextCompat
import at.steinbacher.android_animated_stick_view.R
import at.steinbacher.android_animated_stick_view.Line
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

class LineDrawable(context : Context,
                   val sourceLine : Line,
                   val translatedStartPoint : PointF,
                   val translatedEndPoint: PointF, horizontalLinesCount: Int,
                   verticalLinesCount: Int, width: Float, height: Float, tag: String
): SimpleDrawable(context, horizontalLinesCount, verticalLinesCount, width, height, tag) {

    override fun draw(canvas: Canvas) {
        val vectorDrawable = sourceLine.vectorDrawable
        if(vectorDrawable != null) {
            val width = abs(translatedStartPoint.x - translatedEndPoint.x)
            val height = abs(translatedStartPoint.y - translatedEndPoint.y)
            val stickLength = sqrt(width.toDouble().pow(2.0) + height.toDouble().pow(2.0))
            val alpha = getRotationDegrees(width, height, stickLength)

            vectorDrawable.setBounds(translatedStartPoint.x.toInt(), (translatedStartPoint.y - 50).toInt(),
                (translatedStartPoint.x + stickLength).toInt(), (translatedStartPoint.y + 50).toInt())

            canvas.save()
            canvas.rotate(alpha, translatedStartPoint.x, translatedStartPoint.y)
            vectorDrawable.draw(canvas)
            canvas.restore()
        } else {
            canvas.drawLine(translatedStartPoint.x, translatedStartPoint.y,
                translatedEndPoint.x, translatedEndPoint.y,
                sourceLine.paint)
        }
    }

    private fun getRotationDegrees(width : Float, height : Float, stickLength : Double) : Float {
        var alpha = 0f
        if (width == 0f && translatedEndPoint.y > translatedStartPoint.y) {
            alpha = -270f
        } else if (width == 0f && translatedEndPoint.y < translatedStartPoint.y) {
            alpha = -90f
        } else if (height == 0f && translatedEndPoint.x > translatedStartPoint.x) {
            alpha = 0f
        } else if (height == 0f && translatedEndPoint.x < translatedStartPoint.x) {
            alpha = -180f
        } else {
            alpha = Math.toDegrees(asin(height / stickLength)).toFloat()
            if (translatedEndPoint.x < translatedStartPoint.x && translatedEndPoint.y < translatedStartPoint.y) {
                alpha = -(90 + (90 - alpha))
            } else if (translatedEndPoint.x < translatedStartPoint.x && translatedEndPoint.y > translatedStartPoint.y) {
                alpha = -(180 + alpha)
            } else if (translatedEndPoint.x > translatedStartPoint.x && translatedEndPoint.y < translatedStartPoint.y) {
                alpha = -alpha
            } else if (translatedEndPoint.x > translatedStartPoint.x && translatedEndPoint.y > translatedStartPoint.y) {
                alpha = -(270 + (90 - alpha))
            }
        }

        return alpha
    }
}