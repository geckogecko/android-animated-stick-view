package at.steinbacher.android_animated_stick_view.util

import at.steinbacher.android_animated_stick_view.Circle
import at.steinbacher.android_animated_stick_view.Line
import at.steinbacher.android_animated_stick_view.Rectangle

class SimpleUtil {
    companion object {
        fun moveLine(line: Line, distance: Float, targetAxis: SceneUtil.TargetAxis): Line {
            when (targetAxis) {
                SceneUtil.TargetAxis.X -> {
                    line.startPoint.x += distance
                    line.endPoint.x += distance
                }
                SceneUtil.TargetAxis.Y -> {
                    line.startPoint.y += distance
                    line.endPoint.y += distance
                }
            }

            return line
        }

        fun moveCircle(circle: Circle, distance: Float, targetAxis: SceneUtil.TargetAxis): Circle {
            when (targetAxis) {
                SceneUtil.TargetAxis.X -> circle.middlePoint.x += distance
                SceneUtil.TargetAxis.Y -> circle.middlePoint.y += distance
            }

            return circle
        }

        fun moveRectangle(rectangle: Rectangle, distance: Float, targetAxis: SceneUtil.TargetAxis): Rectangle {
            when (targetAxis) {
                SceneUtil.TargetAxis.X -> {
                    rectangle.topLeft.x += distance
                    rectangle.bottomRight.x += distance
                }
                SceneUtil.TargetAxis.Y -> {
                    rectangle.topLeft.y += distance
                    rectangle.bottomRight.y += distance
                }
            }

            return rectangle
        }
    }
}