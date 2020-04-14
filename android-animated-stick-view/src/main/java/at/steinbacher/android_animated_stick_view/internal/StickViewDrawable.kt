package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable

abstract class StickViewDrawable(val context: Context,
                                 val horizontalLinesCount: Int,
                                 val verticalLinesCount: Int,
                                 val width: Float,
                                 val height: Float) : Drawable() {

    protected val cellWidth: Float = width / verticalLinesCount
    protected val cellHeight: Float = height / horizontalLinesCount

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