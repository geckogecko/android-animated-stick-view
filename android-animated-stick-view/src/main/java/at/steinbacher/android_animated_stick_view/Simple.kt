package at.steinbacher.android_animated_stick_view

import android.graphics.Paint
import android.graphics.PointF
import org.json.JSONObject

abstract class Simple(val paint: Paint, val tag: String) {

    companion object {
        fun pointToJson(point: PointF) = JSONObject().also {
            it.put("x", point.x)
            it.put("y", point.y)
        }

        fun pointFromJson(json: JSONObject) = PointF(json.getDouble("x").toFloat(),
            json.getDouble("y").toFloat()
        )
    }

    abstract fun getCopy(): Simple
    abstract fun toJson(): JSONObject
    abstract fun getSimpleName(): String


    override fun toString(): String = "Tag: $tag"
}