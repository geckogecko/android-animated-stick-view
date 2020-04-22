package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import androidx.core.content.ContextCompat
import at.steinbacher.android_animated_stick_view.R

class GridDrawable(context: Context,
                   horizontalLinesCount: Int,
                   verticalLinesCount: Int,
                   width: Float,
                   height: Float,
                   tag: String) : SimpleDrawable(context, horizontalLinesCount, verticalLinesCount,
    width, height, tag) {

    var gridPaint: Paint = Paint().apply { this.color = ContextCompat.getColor(context, R.color.black) }

    override fun draw(canvas: Canvas) {
        for (i in 0..horizontalLinesCount) {
            val targetY = i*cellHeight
            canvas.drawLine(0F, targetY, width, targetY, gridPaint)
        }

        for (i in 0..verticalLinesCount) {
            val targetX = i*cellWidth
            canvas.drawLine(targetX, 0F, targetX, height, gridPaint)
        }
    }

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