package at.steinbacher.animatedStickViewExample

import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import at.steinbacher.android_animated_stick_view.*
import at.steinbacher.android_animated_stick_view.util.SceneUtil
import java.nio.file.Files.move

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private var stickView: StickView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stickView = findViewById(R.id.stick_view)

        findViewById<AppCompatButton>(R.id.save).setOnClickListener {
            Log.i(TAG, stickView?.getCurrentScene()?.toJson().toString())
        }

        //it.vectorDrawable = resources.getDrawable(R.drawable.ic_trending_flat_24px) as VectorDrawable?

        val paint = Paint().apply {
            color = getColor(R.color.black)
            strokeWidth = 5f
        }

        val gridPaint = Paint().apply {
            color = getColor(R.color.grey)
            strokeWidth = 1f
        }

        val scene = Scene().also {
            it.simples.add(Grid(gridPaint, "grid"))
            it.simples.add(Circle(PointF(4F,3.5F), 0.5F, paint,"head"))
            it.simples.add(Line(PointF(4F,4F), PointF(4F,4.5F), paint,"neck"))
            it.simples.add(Rectangle(PointF(3F,4.5F), PointF(5F,8F), paint,"body").also {
                rectangle ->  rectangle.setVectorDrawable(R.drawable.ic_torso, this)
            })

            it.simples.add(Line(PointF(4.5F,5F), PointF(5F,6F), paint,"right_lower_arm").also {
                line ->  line.setVectorDrawable(R.drawable.ic_trending_flat_24px, this)
            })
            it.simples.add(Line(PointF(5F,6F), PointF(5F,7F), paint,"right_upper_arm").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_trending_flat_24px, this)
            })
            it.simples.add(Line(PointF(3.5F,5F), PointF(3F,6F), paint,"left_lower_arm").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_trending_flat_24px, this)
            })
            it.simples.add(Line(PointF(3F,6F), PointF(3F,7F), paint,"left_upper_arm").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_trending_flat_24px, this)
            })

            it.simples.add(Line(PointF(4.5F,7.5F), PointF(5.5F,9F), paint,"right_upper_foot").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_trending_flat_24px, this)
            })
            it.simples.add(Line(PointF(5.5F,9F), PointF(5.5F,10.5F), paint,"right_lower_foot").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_trending_flat_24px, this)
            })
            it.simples.add(Line(PointF(3.5F,7.5F), PointF(2.5F,9F), paint,"left_upper_foot").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_trending_flat_24px, this)
            })
            it.simples.add(Line(PointF(2.5F,9F), PointF(2.5F,10.5F), paint,"left_lower_foot").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_trending_flat_24px, this)
            })
        }

        val sceneCollection = SceneCollection().apply {
            scenes.add(scene)
        }

        stickView?.let {
            it.setVerticalLinesCount(10)
            it.enableDynamicHorizontalLinesCount(true)
            it.setSceneCollection(sceneCollection)
            it.setAnimationDuration(1000)
            it.startAnimation()
        }
    }
}
