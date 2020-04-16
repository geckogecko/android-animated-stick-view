package at.steinbacher.animatedStickViewExample

import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.steinbacher.android_animated_stick_view.Scene
import at.steinbacher.android_animated_stick_view.Stick
import at.steinbacher.android_animated_stick_view.StickView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //it.vectorDrawable = resources.getDrawable(R.drawable.ic_trending_flat_24px) as VectorDrawable?

        val scene = Scene().also {
            it.sticks.add(Stick(PointF(0F,0F), PointF(1F,0F), "side_top"))
            it.sticks.add(Stick(PointF(1F,0F), PointF(1F,1F), "side_right"))
            it.sticks.add(Stick(PointF(1F,1F), PointF(0F,1F), "side_bottom"))
            it.sticks.add(Stick(PointF(0F,1F), PointF(0F,0F), "side_left"))
        }

        val scene2 = Scene().also {
            it.sticks.add(Stick(PointF(1F,1F), PointF(2F,1F), "side_top"))
            it.sticks.add(Stick(PointF(2F,1F), PointF(2F,2F), "side_right"))
            it.sticks.add(Stick(PointF(2F,2F), PointF(1F,2F), "side_bottom"))
            it.sticks.add(Stick(PointF(1F,2F), PointF(1F,1F), "side_left"))
        }

        val scene3 = Scene().also {
            it.sticks.add(Stick(PointF(2F,1F), PointF(2F,2F), "side_top"))
            it.sticks.add(Stick(PointF(2F,2F), PointF(1F,2F), "side_right"))
            it.sticks.add(Stick(PointF(1F,2F), PointF(1F,1F), "side_bottom"))
            it.sticks.add(Stick(PointF(1F,1F), PointF(2F,1F), "side_left"))
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
