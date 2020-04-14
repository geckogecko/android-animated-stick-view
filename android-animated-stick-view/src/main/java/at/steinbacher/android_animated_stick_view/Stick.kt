package at.steinbacher.android_animated_stick_view

import android.graphics.PointF
import android.graphics.drawable.VectorDrawable

class Stick(var startPointF: PointF, var endPointF: PointF, var tag: String) {
    var vectorDrawable: VectorDrawable? = null

    override fun toString(): String = "StartPointF: $startPointF, EndPointF: $endPointF, Tag: $tag"
}