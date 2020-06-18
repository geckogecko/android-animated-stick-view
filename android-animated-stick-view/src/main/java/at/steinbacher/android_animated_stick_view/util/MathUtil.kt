package at.steinbacher.android_animated_stick_view.util

import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sqrt

class MathUtil {
    companion object {
        fun distanceBetweenPoints(x: Float, y:Float, x1: Float, y1: Float): Float
                = hypot(x-x1, y-y1)
    }
}