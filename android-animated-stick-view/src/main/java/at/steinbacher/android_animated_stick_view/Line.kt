package at.steinbacher.android_animated_stick_view

import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.VectorDrawable

class Line(var startPoint: PointF, var endPoint: PointF, paint: Paint, tag: String): Simple(paint, tag) {
    var vectorDrawable: VectorDrawable? = null

    override fun toString(): String = "StartPoint: $startPoint, EndPoint: $endPoint, Tag: $tag"
}