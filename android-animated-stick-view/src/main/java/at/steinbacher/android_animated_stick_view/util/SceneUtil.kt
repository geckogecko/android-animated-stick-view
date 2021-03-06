package at.steinbacher.android_animated_stick_view.util

import at.steinbacher.android_animated_stick_view.*

class SceneUtil {

    enum class TargetAxis {
        X, Y
    }
    companion object {
        fun move(scene: Scene, distance: Float, targetAxis: TargetAxis): Scene {
            for(i in scene.simples.indices) {
                when (val simple = scene.simples[i]) {
                    is Line -> {
                       scene.simples[i] = SimpleUtil.moveLine(simple, distance, targetAxis)
                    }
                    is Circle -> {
                        scene.simples[i] = SimpleUtil.moveCircle(simple, distance, targetAxis)
                    }
                    is Rectangle -> {
                        scene.simples[i] = SimpleUtil.moveRectangle(simple, distance, targetAxis)
                    }
                }
            }

            return scene
        }
    }
}