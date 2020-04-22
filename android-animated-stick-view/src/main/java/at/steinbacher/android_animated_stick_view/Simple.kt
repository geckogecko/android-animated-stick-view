package at.steinbacher.android_animated_stick_view

import android.graphics.Paint

open class Simple(val paint: Paint, val tag: String) {
    override fun toString(): String = "Tag: $tag"
}