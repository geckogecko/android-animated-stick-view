package at.steinbacher.android_animated_stick_view

import android.graphics.Paint
import android.graphics.PointF
import org.json.JSONObject

class Circle(var middlePoint: PointF, var radius: Float, paint: Paint, tag: String): Simple(paint, tag) {

    companion object {
        fun fromJson(jsonObject: JSONObject): Circle {
            return Circle(pointFromJson(jsonObject.getJSONObject("middlePoint")),
                jsonObject.getDouble("radius").toFloat(),
                Paint(),
                jsonObject.getString("tag"))
        }
    }

    override fun getCopy(): Simple {
        return Circle(PointF(middlePoint.x, middlePoint.y), radius, paint, tag)
    }

    override fun toJson() = JSONObject().also {
        it.put("middlePoint", pointToJson(middlePoint))
        it.put("radius", radius)
        it.put("tag", tag)
    }

    override fun getSimpleName() = "Circle"

    override fun toString(): String = "MiddlePointF: $middlePoint, Tag: $tag"
}