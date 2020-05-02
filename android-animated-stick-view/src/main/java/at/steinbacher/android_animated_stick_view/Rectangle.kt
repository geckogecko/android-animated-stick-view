package at.steinbacher.android_animated_stick_view

import android.graphics.Paint
import android.graphics.PointF

class Rectangle(val topLeft: PointF,
                val bottomRight: PointF,
                paint: Paint,
                tag: String)
    : Simple(paint, tag){

    override fun getCopy(): Simple {
        return Rectangle(PointF(topLeft.x, topLeft.y),
            PointF(bottomRight.x, bottomRight.y),
            paint,
            tag)
    }
}