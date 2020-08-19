package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import at.steinbacher.android_animated_stick_view.R

abstract class SimpleDrawable(val context: Context,
                              val horizontalLinesCount: Int,
                              val verticalLinesCount: Int,
                              val width: Float,
                              val height: Float,
                              var tag: String) : Drawable() {

    protected val cellWidth: Float = width / verticalLinesCount
    protected val cellHeight: Float = height / horizontalLinesCount

    var highlighted: Boolean = false

    protected val highlightPaint = Paint().apply {
        color = context.getColor(R.color.red)
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    abstract fun distanceTo(x: Float, y: Float): Float
    abstract fun move(moveX: Float, moveY: Float)
    abstract fun rotate(angle: Float)

    override fun setAlpha(alpha: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOpacity(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}