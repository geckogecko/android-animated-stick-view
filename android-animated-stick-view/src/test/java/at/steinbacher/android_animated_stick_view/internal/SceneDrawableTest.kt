package at.steinbacher.android_animated_stick_view.internal

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import org.junit.Test
import androidx.test.platform.app.InstrumentationRegistry
import at.steinbacher.android_animated_stick_view.Circle
import at.steinbacher.android_animated_stick_view.Grid
import at.steinbacher.android_animated_stick_view.Line
import at.steinbacher.android_animated_stick_view.Simple

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SceneDrawableTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private var sceneDrawable: SceneDrawable = SceneDrawable(context,
        5,
        5,
        500F,
        500F,
        "scene")

    @Before
    fun setup() {
        val paint = Paint()

        val line = Line(PointF(0F,0F), PointF(1F,1F), paint, "line")
        val grid = Grid(paint, "gird")
        val circle = Circle(PointF(0F,0F), 2F, paint, "circle")

        sceneDrawable.addSimpleDrawable(line)
        sceneDrawable.addSimpleDrawable(grid)
        sceneDrawable.addSimpleDrawable(circle)
    }

    @Test
    fun addSimpleDrawableTest() {
        //Line
        assertTrue(sceneDrawable.getSimpleDrawables()[0] is LineDrawable)

        //Grid
        assertTrue(sceneDrawable.getSimpleDrawables()[1] is GridDrawable)

        //Circle
        assertTrue(sceneDrawable.getSimpleDrawables()[2] is CircleDrawable)
    }
}