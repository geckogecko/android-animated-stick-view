package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import at.steinbacher.android_animated_stick_view.Stick

class SceneDrawable(context: Context,
                    horizontalLinesCount: Int,
                    verticalLinesCount: Int,
                    width: Float,
                    height: Float) : StickViewDrawable(context,
    horizontalLinesCount, verticalLinesCount, width, height) {

    private val stickDrawables : MutableList<StickDrawable> = ArrayList()

    fun addStick(stick : Stick) {
        val translatedStartPoint = PointF(stick.startPointF.x * cellWidth,
            stick.startPointF.y * cellHeight)

        val translatedEndPoint = PointF(stick.endPointF.x * cellWidth,
            stick.endPointF.y * cellHeight)

        StickDrawable(context, stick, translatedStartPoint, translatedEndPoint).also {
            stickDrawables.add(it)
        }
    }

    override fun draw(canvas : Canvas) {
        for(stickDrawable in stickDrawables) {
            stickDrawable.draw(canvas)
        }
    }

    fun contains(stickTag : String) : Boolean {
        for(stickDrawable in stickDrawables) {
            if(stickDrawable.tag == stickTag) return true
        }

        return false
    }

    fun getStickDrawable(stickTag : String) : StickDrawable? {
        for(stickDrawable in stickDrawables) {
            if(stickDrawable.tag == stickTag) return stickDrawable
        }

        return null
    }

    fun updateStickDrawable(updateStickDrawable: StickDrawable) {
        for(i in 0 until stickDrawables.size) {
            if(stickDrawables[i].tag == updateStickDrawable.tag) {
                this.stickDrawables[i] = updateStickDrawable
            }
        }
    }

    fun getStickDrawables() : List<StickDrawable> {
        return stickDrawables
    }

    fun clearSticks() {
        stickDrawables.clear()
    }
}