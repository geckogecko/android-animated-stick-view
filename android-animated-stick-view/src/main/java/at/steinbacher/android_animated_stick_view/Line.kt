package at.steinbacher.android_animated_stick_view

import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.VectorDrawable
import org.json.JSONObject

class Line(var startPoint: PointF, var endPoint: PointF, paint: Paint, tag: String): Simple(paint, tag) {
    var vectorDrawable: VectorDrawable? = null

    companion object {
        fun fromJson(jsonObject: JSONObject): Line {
            return Line(pointFromJson(jsonObject.getJSONObject("startPoint")),
                pointFromJson(jsonObject.getJSONObject("endPoint")),
                paintFromJson(jsonObject.getJSONObject("paint")),
                jsonObject.getString("tag"))
        }
    }

    override fun getCopy(): Simple {
        return Line(
            PointF(startPoint.x, startPoint.y),
            PointF(endPoint.x, endPoint.y),
            paint,
            tag)
    }

    override fun toJson()= JSONObject().also {
        it.put("startPoint", pointToJson(startPoint))
        it.put("endPoint", pointToJson(endPoint))
        it.put("paint", paintToJson(paint))
        it.put("tag", tag)
    }

    override fun getSimpleName() = "Line"

    override fun toString(): String = "StartPoint: $startPoint, EndPoint: $endPoint, Tag: $tag"
}