package at.steinbacher.android_animated_stick_view

import android.graphics.Paint
import android.graphics.PointF

class Circle(var middlePointF: PointF, var radius: Float, paint: Paint, tag: String): Simple(paint, tag) {
    override fun toString(): String = "MiddlePointF: $middlePointF, Tag: $tag"
}