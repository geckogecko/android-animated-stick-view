package at.steinbacher.android_animated_stick_view

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class SceneCollection {
    var scenes: MutableList<Scene> = ArrayList()

    companion object {
        fun fromJson(jsonArray: JSONArray, context: Context): SceneCollection {
            return SceneCollection().also {
                for(i in 0 until jsonArray.length()) {
                    it.scenes.add(Scene.fromJson(jsonArray[i] as JSONArray, context))
                }
            }
        }
    }

    fun toJson()= JSONArray().also {
        scenes.forEach { item ->
            it.put(item.toJson())
        }
    }
}