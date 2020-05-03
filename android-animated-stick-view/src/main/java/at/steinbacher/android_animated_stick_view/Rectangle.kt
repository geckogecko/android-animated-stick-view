package at.steinbacher.android_animated_stick_view

import android.content.Context
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.core.graphics.drawable.DrawableCompat
import org.json.JSONObject

class Rectangle(val topLeft: PointF,
                val bottomRight: PointF,
                paint: Paint,
                tag: String)
    : Simple(paint, tag){

    private var vectorDrawableInt: Int = -1
    private var vectorDrawable: VectorDrawable? = null

    companion object {
        fun fromJson(jsonObject: JSONObject, context: Context): Rectangle {
            val rectangle = Rectangle(pointFromJson(jsonObject.getJSONObject("topLeft")),
                pointFromJson(jsonObject.getJSONObject("bottomRight")),
                paintFromJson(jsonObject.getJSONObject("paint")),
                jsonObject.getString("tag"))

            if(jsonObject.has("drawable")) {
                rectangle.setVectorDrawable(jsonObject.getInt("drawable"), context)
            }

            return rectangle
        }
    }

    fun setVectorDrawable(vectorDrawableInt: Int, context: Context) {
        this.vectorDrawableInt = vectorDrawableInt
        vectorDrawable = context.resources.getDrawable(vectorDrawableInt, context.theme) as VectorDrawable?
    }

    fun getVectorDrawable() = vectorDrawable

    override fun getCopy(): Simple {
        return Rectangle(PointF(topLeft.x, topLeft.y),
            PointF(bottomRight.x, bottomRight.y),
            paint,
            tag)
    }

    override fun getSimpleName() = "Rectangle"

    override fun toJson()= JSONObject().also {
        it.put("topLeft", pointToJson(topLeft))
        it.put("bottomRight", pointToJson(bottomRight))
        it.put("paint", paintToJson(paint))

        if(vectorDrawableInt != -1) {
            it.put("drawable", vectorDrawableInt)
        }

        it.put("tag", tag)
    }
}