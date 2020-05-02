package at.steinbacher.android_animated_stick_view

import android.graphics.Paint
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class Scene {
    var simples: MutableList<Simple> = ArrayList()

    companion object {
        fun fromJson(jsonArray: JSONArray): Scene {
            return Scene().also {
                for(i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray[i] as JSONObject
                    when(jsonObject.getString("item_type")) {
                        "Circle" -> it.simples.add(Circle.fromJson(jsonObject.getJSONObject("item")))
                        "Grid" -> it.simples.add(Grid.fromJson(jsonObject.getJSONObject("item")))
                        "Line" -> it.simples.add(Line.fromJson(jsonObject.getJSONObject("item")))
                        "Rectangle" -> it.simples.add(Rectangle.fromJson(jsonObject.getJSONObject("item")))
                    }
                }
            }
        }
    }

    fun get(tag: String): Simple? {
        simples.forEach {
            if(it.tag == tag)
                return it
        }

        return null
    }

    fun toJson()= JSONArray().also {
        simples.forEach { item ->
            val jsonObject = JSONObject()
            jsonObject.put("item_type", item.getSimpleName())
            jsonObject.put("item", item.toJson())
            it.put(jsonObject)
        }
    }

    fun getCopy(): Scene {
        val newScene = Scene()
        simples.forEach {
            newScene.simples.add(it.getCopy())
        }

        return newScene
    }
}