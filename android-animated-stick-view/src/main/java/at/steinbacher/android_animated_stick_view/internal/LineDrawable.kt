package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.Log
import at.steinbacher.android_animated_stick_view.Line
import at.steinbacher.android_animated_stick_view.util.MathUtil
import kotlin.math.*

private const val TAG = "LineDrawable"
class LineDrawable(context : Context,
                   val sourceLine : Line,
                   var translatedStartPoint : PointF,
                   var translatedEndPoint: PointF,
                   var translatedWidth: Float,
                   horizontalLinesCount: Int,
                   verticalLinesCount: Int, width: Float, height: Float, tag: String
): SimpleDrawable(context, horizontalLinesCount, verticalLinesCount, width, height, tag) {

    private val lineLength = MathUtil.distanceBetweenPoints(translatedStartPoint.x, translatedStartPoint.y,
        translatedEndPoint.x, translatedEndPoint.y)

    override fun distanceTo(x: Float, y: Float): Float {
        val distanceToLine = pDistance(x,y, translatedStartPoint.x, translatedStartPoint.y, translatedEndPoint.x, translatedEndPoint.y)
        val distanceToStart = MathUtil.distanceBetweenPoints(x, y, translatedStartPoint.x, translatedStartPoint.y)
        val distanceToEnd = MathUtil.distanceBetweenPoints(x, y, translatedEndPoint.x, translatedEndPoint.y)

        if(distanceToStart > lineLength || distanceToEnd > lineLength) {
            return Float.MAX_VALUE
        }

        return distanceToLine
    }

    override fun move(moveX: Float, moveY: Float) {
        translatedStartPoint.x -= moveX
        translatedStartPoint.y -= moveY

        translatedEndPoint.x -= moveX
        translatedEndPoint.y -= moveY
    }

    override fun rotate(angle: Float) {
        val newX = translatedStartPoint.x + ((translatedEndPoint.x-translatedStartPoint.x) * cos(angle)) - ((translatedEndPoint.y-translatedStartPoint.y) * sin(angle))
        val newY = translatedStartPoint.y + ((translatedEndPoint.x-translatedStartPoint.x) * sin(angle)) + ((translatedEndPoint.y-translatedStartPoint.y) * cos(angle))
        translatedEndPoint = PointF(newX, newY)
    }

    override fun draw(canvas: Canvas) {
        Log.i(TAG, "draw: $translatedWidth")
        val vectorDrawable = sourceLine.getVectorDrawable()
        if(vectorDrawable != null) {
            val width = abs(translatedStartPoint.x - translatedEndPoint.x)
            val height = abs(translatedStartPoint.y - translatedEndPoint.y)
            val stickLength = sqrt(width.toDouble().pow(2.0) + height.toDouble().pow(2.0))
            val alpha = getRotationDegrees(width, height, stickLength)

            vectorDrawable.setBounds(translatedStartPoint.x.toInt(), (translatedStartPoint.y - translatedWidth/2).toInt(),
                (translatedStartPoint.x + stickLength).toInt(), (translatedStartPoint.y + translatedWidth/2).toInt())

            canvas.save()
            canvas.rotate(alpha, translatedStartPoint.x, translatedStartPoint.y)
            vectorDrawable.draw(canvas)
            canvas.restore()

        } else {
            canvas.drawLine(translatedStartPoint.x, translatedStartPoint.y,
                translatedEndPoint.x, translatedEndPoint.y,
                sourceLine.paint)
        }

        if(highlighted) {
            val distance = 10
            val left = translatedStartPoint.x  - distance
            val top = translatedStartPoint.y - distance
            val right = translatedEndPoint.x + distance
            val bottom = translatedEndPoint.y + distance
            canvas.drawRect(left, top, right, bottom, highlightPaint)
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

    //from: https://stackoverflow.com/a/30567488/5183341
    private fun pDistance(
        x: Float,
        y: Float,
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float
    ): Float {
        val A = x - x1 // position of point rel one end of line
        val B = y - y1
        val C = x2 - x1 // vector along line
        val D = y2 - y1
        val E = -D // orthogonal vector
        val dot = A * E + B * C
        val len_sq = E * E + C * C
        return (abs(dot) / sqrt(len_sq.toDouble())).toFloat()
    }
}