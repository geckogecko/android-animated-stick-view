package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import androidx.core.content.ContextCompat
import at.steinbacher.android_animated_stick_view.Grid
import at.steinbacher.android_animated_stick_view.R

class GridDrawable(context: Context,
                   val sourceGrid: Grid,
                   horizontalLinesCount: Int,
                   verticalLinesCount: Int,
                   width: Float,
                   height: Float,
                   tag: String) : SimpleDrawable(context, horizontalLinesCount, verticalLinesCount,
    width, height, tag) {

    //we dont want click events for the grid
    override fun distanceTo(x: Float, y: Float): Float {
        return Float.MAX_VALUE
    }

    //we dont want to allow moving the grid
    override fun move(x: Float, y: Float) {}
    override fun rotate(angle: Float) {}
    override fun scaleHeight(moveY: Float) {}
    override fun scaleWidth(moveX: Float) {}

    override fun draw(canvas: Canvas) {
        for (i in 0..horizontalLinesCount) {
            val targetY = i*cellHeight
            canvas.drawLine(0F, targetY, width, targetY, sourceGrid.paint)
        }

        for (i in 0..verticalLinesCount) {
            val targetX = i*cellWidth
            canvas.drawLine(targetX, 0F, targetX, height, sourceGrid.paint)
        }
    }
}