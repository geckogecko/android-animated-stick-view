package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import androidx.core.content.ContextCompat
import at.steinbacher.android_animated_stick_view.Circle
import at.steinbacher.android_animated_stick_view.R

class CircleDrawable(context : Context,
                     val sourceCircle : Circle,
                     val translatedMiddlePointF: PointF,
                     val translatedRadius: Float,
                     horizontalLinesCount: Int, verticalLinesCount: Int,
                     width: Float, height: Float, tag: String
    ): SimpleDrawable(context, horizontalLinesCount, verticalLinesCount, width, height, tag) {

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(translatedMiddlePointF.x, translatedMiddlePointF.y, translatedRadius, sourceCircle.paint)
    }

}