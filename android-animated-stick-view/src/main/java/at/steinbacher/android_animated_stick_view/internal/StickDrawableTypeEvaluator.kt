package at.steinbacher.android_animated_stick_view.internal

import android.animation.TypeEvaluator
import android.graphics.PointF

class StickDrawableTypeEvaluator : TypeEvaluator<Array<StickDrawable>> {
    override fun evaluate(
        fraction: Float,
        startValues: Array<StickDrawable>?,
        endValues: Array<StickDrawable>?
    ): Array<StickDrawable> {
        val array = ArrayList<StickDrawable>()

        if(startValues != null && endValues != null) {
            for (i in startValues.indices) {
                val start: StickDrawable = startValues[i]
                val end: StickDrawable = endValues[i]

                val startStartPoint: PointF = start.translatedStartPoint
                val endStartPoint: PointF = end.translatedStartPoint
                val interpolatedStartPoint = PointF(
                    startStartPoint.x + fraction * (endStartPoint.x - startStartPoint.x),
                    startStartPoint.y + fraction * (endStartPoint.y - startStartPoint.y)
                )
                val endEndPoint: PointF = end.translatedEndPoint
                val startEndPoint: PointF = start.translatedEndPoint
                val interpolatedEndPoint = PointF(
                    startEndPoint.x + fraction * (endEndPoint.x - startEndPoint.x),
                    startEndPoint.y + fraction * (endEndPoint.y - startEndPoint.y)
                )
                array.add(StickDrawable(start.context, start.sourceStick,
                        interpolatedStartPoint, interpolatedEndPoint))
            }
        }

        return array.toTypedArray()
    }
}