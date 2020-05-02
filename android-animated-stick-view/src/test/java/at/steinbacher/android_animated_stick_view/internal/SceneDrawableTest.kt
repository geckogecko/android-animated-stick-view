package at.steinbacher.android_animated_stick_view.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import org.junit.Test
import at.steinbacher.android_animated_stick_view.Circle
import at.steinbacher.android_animated_stick_view.Grid
import at.steinbacher.android_animated_stick_view.Line
import at.steinbacher.android_animated_stick_view.Simple

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.internal.util.reflection.FieldSetter
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SceneDrawableTest {
    private val horizontalLinesCount = 5
    private val verticalLinesCount = 5
    private val width = 500F
    private val height = 500F

    @Mock
    private lateinit var context: Context

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun addSimpleDrawableTest() {
        val sceneDrawable = SceneDrawable(context,
            horizontalLinesCount,
            verticalLinesCount,
            width,
            height,
            "scene")

        val paint = Paint()

        val line = Line(PointF(0F,0F), PointF(1F,1F), paint, "line")
        val grid = Grid(paint, "gird")
        val circle = Circle(PointF(0F,0F), 2F, paint, "circle")
        val unknownDrawable = Simple(paint, "unknown")

        assertTrue(sceneDrawable.addSimpleDrawable(line))
        assertTrue(sceneDrawable.addSimpleDrawable(grid))
        assertTrue(sceneDrawable.addSimpleDrawable(circle))
        assertFalse(sceneDrawable.addSimpleDrawable(unknownDrawable))
    }

    @Test
    fun drawTest() {
        val mockSceneDrawable = mock(SceneDrawable::class.java)
        val mockSimpleDrawableFactory = mock(SimpleDrawableFactory::class.java)
        val mockLine = mock(Line::class.java)
        val mockLineDrawable = mock(LineDrawable::class.java)
        val mockCanvas = mock(Canvas::class.java)

        //setup mock of SimpleDrawableFactory
        `when`(mockSimpleDrawableFactory.createSimpleDrawable(mockLine))
            .thenReturn(mockLineDrawable)

        //setup mock of SceneDrawable
        FieldSetter.setField(mockSceneDrawable, mockSceneDrawable.javaClass.getDeclaredField("simpleDrawables"),
            ArrayList<SimpleDrawable>())
        `when`(mockSceneDrawable.getSimpleDrawableFactory())
            .thenReturn(mockSimpleDrawableFactory)
        `when`(mockSceneDrawable.addSimpleDrawable(mockLine))
            .thenCallRealMethod()
        `when`(mockSceneDrawable.draw(mockCanvas))
            .thenCallRealMethod()

        assertTrue(mockSceneDrawable.addSimpleDrawable(mockLine))
        mockSceneDrawable.draw(mockCanvas)

        verify(mockLineDrawable, times(1)).draw(any(Canvas::class.java))
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}