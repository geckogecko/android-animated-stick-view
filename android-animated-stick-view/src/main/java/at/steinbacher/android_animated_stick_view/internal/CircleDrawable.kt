package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import androidx.core.content.ContextCompat
import at.steinbacher.android_animated_stick_view.Circle
import at.steinbacher.android_animated_stick_view.R
import at.steinbacher.android_animated_stick_view.util.MathUtil
import kotlin.math.pow
import kotlin.math.sqrt

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

    override fun moveTo(x: Float, y: Float) {
        translatedMiddlePointF = PointF(x, y)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(translatedMiddlePointF.x, translatedMiddlePointF.y, translatedRadius, sourceCircle.paint)
    }

}