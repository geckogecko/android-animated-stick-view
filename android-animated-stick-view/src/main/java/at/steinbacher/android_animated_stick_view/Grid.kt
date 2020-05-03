package at.steinbacher.android_animated_stick_view

import android.graphics.Paint
import org.json.JSONObject

class Grid(paint: Paint, tag: String) : Simple(paint, tag){

    companion object {
        fun fromJson(jsonObject: JSONObject): Grid {
            return Grid(paintFromJson(jsonObject.getJSONObject("paint")),
                jsonObject.getString("tag"))
        }
    }

    override fun getCopy(): Simple {
        return Grid(paint, tag)
    }

    override fun toJson()= JSONObject().also {
        it.put("paint", paintToJson(paint))
        it.put("tag", tag)
    }

    override fun getSimpleName() = "Grid"

}