package at.steinbacher.android_animated_stick_view

import android.graphics.Paint
import android.graphics.PointF
import org.json.JSONObject

class Rectangle(val topLeft: PointF,
                val bottomRight: PointF,
                paint: Paint,
                tag: String)
    : Simple(paint, tag){

    companion object {
        fun fromJson(jsonObject: JSONObject): Rectangle {
            return Rectangle(pointFromJson(jsonObject.getJSONObject("topLeft")),
                pointFromJson(jsonObject.getJSONObject("bottomRight")),
                Paint(),
                jsonObject.getString("tag"))
        }
    }

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
        it.put("tag", tag)
    }
}