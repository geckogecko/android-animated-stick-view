package at.steinbacher.android_animated_stick_view.internal

import android.animation.TypeEvaluator
import android.graphics.PointF
import android.util.Log

class SimpleDrawableTypeEvaluator : TypeEvaluator<Array<SimpleDrawable>> {
    override fun evaluate(
        fraction: Float,
        startValues: Array<SimpleDrawable>?,
        endValues: Array<SimpleDrawable>?
    ): Array<SimpleDrawable> {
        val array = ArrayList<SimpleDrawable>()

        if(startValues != null && endValues != null) {
            for (i in startValues.indices) {
                if(startValues[i] is StickDrawable && endValues[i] is StickDrawable) {
                    val start: StickDrawable = startValues[i] as StickDrawable
                    val end: StickDrawable = endValues[i] as StickDrawable

                    val interpolatedStartPoint = PointF(
                        start.translatedStartPoint.x + fraction
                                * (end.translatedStartPoint.x - start.translatedStartPoint.x),
                        start.translatedStartPoint.y + fraction
                                * (end.translatedStartPoint.y - start.translatedStartPoint.y))

                    val interpolatedEndPoint = PointF(
                        start.translatedEndPoint.x + fraction
                                * (end.translatedEndPoint.x - start.translatedEndPoint.x),
                        start.translatedEndPoint.y + fraction
                                * (end.translatedEndPoint.y - start.translatedEndPoint.y))

                    array.add(
                        StickDrawable(
                            start.context,
                            start.sourceStick,
                            interpolatedStartPoint,
                            interpolatedEndPoint,
                            start.horizontalLinesCount,
                            start.verticalLinesCount,
                            start.width,
                            start.height,
                            start.tag
                        )
                    )
                } else if(startValues[i] is CircleDrawable && endValues[i] is CircleDrawable) {
                    val start: CircleDrawable = startValues[i] as CircleDrawable
                    val end: CircleDrawable = endValues[i] as CircleDrawable

                    val interpolatedRadius = start.translatedRadius + fraction * (end.translatedRadius - start.translatedRadius)

                    val interpolatedMiddlePoint = PointF(
                        start.translatedMiddlePointF.x + fraction
                                * (end.translatedMiddlePointF.x - start.translatedMiddlePointF.x),
                        start.translatedMiddlePointF.y + fraction
                                * (end.translatedMiddlePointF.y - start.translatedMiddlePointF.y))

                    array.add(
                        CircleDrawable(
                            start.context,
                            start.sourceCircle,
                            interpolatedMiddlePoint,
                            interpolatedRadius,
                            start.horizontalLinesCount,
                            start.verticalLinesCount,
                            start.width,
                            start.height,
                            start.tag
                        )
                    )
                }
            }
        }

        return array.toTypedArray()
    }
}