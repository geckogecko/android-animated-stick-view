package at.steinbacher.animatedStickViewExample

import android.graphics.PointF
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.steinbacher.android_animated_stick_view.Scene
import at.steinbacher.android_animated_stick_view.Stick
import at.steinbacher.android_animated_stick_view.StickView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val scene = Scene().also {
            it.sticks.add(Stick(PointF(1F,1F), PointF(2F,2F), "test").also { it ->
                it.vectorDrawable = resources.getDrawable(R.drawable.ic_trending_flat_24px) as VectorDrawable?
            })
        }

        val scene2 = Scene().also {
            it.sticks.add(Stick(PointF(2F,2F), PointF(3F,3F), "test"))
        }

        val scene3 = Scene().also {
            it.sticks.add(Stick(PointF(3F,2F), PointF(4F,2F), "test"))
        }

        val scenes = ArrayList<Scene>().also {
            it.add(scene)
            it.add(scene2)
            it.add(scene3)
        }

        findViewById<StickView>(R.id.stick_view).let {
            it.setScenes(scenes)
            it.startAnimation()
        }
    }
}
