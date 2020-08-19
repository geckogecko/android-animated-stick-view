package at.steinbacher.android_animated_stick_view

import android.content.Context
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.VectorDrawable
import org.json.JSONObject

class Line(var startPoint: PointF,
           var endPoint: PointF,
           var width: Float,
           paint: Paint,
           tag: String
): Simple(paint, tag) {
    private var vectorDrawableInt: Int = -1
    private var vectorDrawable: VectorDrawable? = null

    companion object {
        fun fromJson(jsonObject: JSONObject, context: Context): Line {
            val line = Line(pointFromJson(jsonObject.getJSONObject("startPoint")),
                pointFromJson(jsonObject.getJSONObject("endPoint")),
                jsonObject.getDouble("width").toFloat(),
                paintFromJson(jsonObject.getJSONObject("paint")),
                jsonObject.getString("tag"))

            if(jsonObject.has("drawable")) {
                line.setVectorDrawable(jsonObject.getInt("drawable"), context)
            }

            return line
        }
    }

    override fun getCopy(): Simple {
        return Line(
            PointF(startPoint.x, startPoint.y),
            PointF(endPoint.x, endPoint.y),
            width,
            paint,
            tag)
    }

    override fun toJson()= JSONObject().also {
        it.put("startPoint", pointToJson(startPoint))
        it.put("endPoint", pointToJson(endPoint))
        it.put("width", width)
        it.put("paint", paintToJson(paint))

        if(vectorDrawableInt != -1) {
            it.put("drawable", vectorDrawableInt)
        }

        it.put("tag", tag)
    }

    fun setVectorDrawable(vectorDrawableInt: Int, context: Context) {
        this.vectorDrawableInt = vectorDrawableInt
        vectorDrawable = context.resources.getDrawable(vectorDrawableInt, context.theme) as VectorDrawable?
    }

    fun getVectorDrawable() = this.vectorDrawable

    override fun getSimpleName() = "Line"

    override fun toString(): String = "StartPoint: $startPoint, EndPoint: $endPoint, Tag: $tag"
}