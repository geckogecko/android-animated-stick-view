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
            it.color = getColor(R.color.red)
        }

        val scene = Scene().also {
            it.simples.add(Rectangle(PointF(4F,4F), PointF(5F,5F), paint,"rectangle"))
            it.simples.add(Rectangle(PointF(4F,4F), PointF(5F,5F), paint,"rectangle2").also { rect ->
                rect.setVectorDrawable(R.drawable.ic_trending_flat_24px, applicationContext)
            })
        }

        val scene2 = SceneUtil.move(scene.getCopy(), 2F, SceneUtil.TargetAxis.X)

        val sceneCollection = SceneCollection().also {
            it.scenes.add(scene)
            it.scenes.add(scene2)
        }

        val testSceneCollection = SceneCollection.fromJson(sceneCollection.toJson(), applicationContext)

        findViewById<StickView>(R.id.stick_view).let {
            it.setGrid(10, 10)
            it.enableDynamicHorizontalLinesCount(true)
            it.setSceneCollection(testSceneCollection)
            it.startAnimation()
        }
    }
}
