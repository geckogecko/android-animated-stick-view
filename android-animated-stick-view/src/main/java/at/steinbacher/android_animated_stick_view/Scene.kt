package at.steinbacher.android_animated_stick_view

class Scene {
    var simples: MutableList<Simple> = ArrayList()

    fun get(tag: String): Simple? {
        simples.forEach {
            if(it.tag == tag)
                return it
        }

        return null
    }

    fun getCopy(): Scene {
        val newScene = Scene()
        simples.forEach {
            newScene.simples.add(it.getCopy())
        }

        return newScene
    }
}