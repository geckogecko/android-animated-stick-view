package at.steinbacher.animatedStickViewExample

import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import at.steinbacher.android_animated_stick_view.*
import at.steinbacher.android_animated_stick_view.util.SceneUtil
import java.nio.file.Files.move

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //it.vectorDrawable = resources.getDrawable(R.drawable.ic_trending_flat_24px) as VectorDrawable?

        val paint = Paint().also {
            it.color = getColor(R.color.black)
            it.strokeWidth = 5f
        }

        val scene = Scene().also {
            it.simples.add(Line(PointF(4F,5F), PointF(6F,5F), paint,"line"))
        }

        val scene2 = SceneUtil.move(scene.getCopy(), -2F, SceneUtil.TargetAxis.Y)

        val sceneCollection = SceneCollection().also {
            it.scenes.add(scene)
            it.scenes.add(scene2)
        }

        findViewById<StickView>(R.id.stick_view).let {
            it.setVerticalLinesCount(10)
            it.enableDynamicHorizontalLinesCount(true)
            it.setSceneCollection(sceneCollection)
            it.startAnimation()
        }
    }
}
