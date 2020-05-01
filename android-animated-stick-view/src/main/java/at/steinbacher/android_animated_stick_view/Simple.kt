package at.steinbacher.android_animated_stick_view

import android.graphics.Paint

abstract class Simple(val paint: Paint, val tag: String) {

    abstract fun getCopy(): Simple

    override fun toString(): String = "Tag: $tag"
}