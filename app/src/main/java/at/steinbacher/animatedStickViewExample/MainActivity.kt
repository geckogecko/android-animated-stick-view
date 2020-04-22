package at.steinbacher.animatedStickViewExample

import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.steinbacher.android_animated_stick_view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //it.vectorDrawable = resources.getDrawable(R.drawable.ic_trending_flat_24px) as VectorDrawable?

        val paint = Paint(getColor(R.color.black))

        val scene = Scene().also {
            it.simples.add(Circle(PointF(4F,4F), 2F, paint,"circle"))
            it.simples.add(Line(PointF(0F,0F), PointF(1F,0F), paint, "side_top"))
            it.simples.add(Line(PointF(1F,0F), PointF(1F,1F), paint, "side_right"))
            it.simples.add(Line(PointF(1F,1F), PointF(0F,1F), paint, "side_bottom"))
            it.simples.add(Line(PointF(0F,1F), PointF(0F,0F), paint, "side_left"))
        }

        val scene2 = Scene().also {
            it.simples.add(Circle(PointF(5F,4F), 1F, paint, "circle"))
            it.simples.add(Line(PointF(1F,1F), PointF(2F,1F), paint, "side_top"))
            it.simples.add(Line(PointF(2F,1F), PointF(2F,2F), paint, "side_right"))
            it.simples.add(Line(PointF(2F,2F), PointF(1F,2F), paint, "side_bottom"))
            it.simples.add(Line(PointF(1F,2F), PointF(1F,1F), paint, "side_left"))
        }

        val scene3 = Scene().also {
            it.simples.add(Circle(PointF(4F,4F), 2F, paint, "circle"))
            it.simples.add(Line(PointF(2F,1F), PointF(2F,2F), paint, "side_top"))
            it.simples.add(Line(PointF(2F,2F), PointF(1F,2F), paint, "side_right"))
            it.simples.add(Line(PointF(1F,2F), PointF(1F,1F), paint, "side_bottom"))
            it.simples.add(Line(PointF(1F,1F), PointF(2F,1F), paint, "side_left"))
        }


        val scenes = ArrayList<Scene>().also {
            it.add(scene)
            it.add(scene2)
            it.add(scene3)
        }

        findViewById<StickView>(R.id.stick_view).let {
            it.setGrid(10, 10)
            it.enableDynamicHorizontalLinesCount(true)
            it.setScenes(scenes)
            it.startAnimation()
        }
    }
}
