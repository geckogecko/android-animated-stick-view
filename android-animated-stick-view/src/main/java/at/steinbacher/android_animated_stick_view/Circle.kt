package at.steinbacher.android_animated_stick_view

import android.graphics.PointF

class Circle(var middlePointF: PointF, var radius: Float, tag: String): Simple(tag) {
    override fun toString(): String = "MiddlePointF: $middlePointF, Tag: $tag"
}