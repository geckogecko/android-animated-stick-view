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

            it.simples.add(Rectangle(PointF(3.7F,3.7F), PointF(4.3F,5F), paint,"neck").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_neck, this)
            })

            it.simples.add(Rectangle(PointF(3.2F,2.5F), PointF(4.8F,4.3F), paint,"head").also {
                    circle ->  circle.setVectorDrawable(R.drawable.ic_head, this)
            })

            it.simples.add(Line(PointF(4.9F,4.8F), PointF(5.6F,7F), paint,"right_upper_arm").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_upper_arm, this)
            })
            it.simples.add(Line(PointF(5.6F,6.7F), PointF(5.6F,8.9F), paint,"right_lower_arm").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_lower_arm, this)
            })
            it.simples.add(Line(PointF(3.1F,4.8F), PointF(2.5F,7F), paint,"left_upper_arm").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_upper_arm, this)
            })
            it.simples.add(Line(PointF(2.52F,6.7F), PointF(2.5F,8.9F), paint,"left_lower_arm").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_lower_arm, this)
            })

            it.simples.add(Line(PointF(4.5F,7.5F), PointF(5.5F,10.5F), paint,"right_upper_foot").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_upper_foot, this)
            })
            it.simples.add(Line(PointF(5.4F,10F), PointF(5.5F,12F), paint,"right_lower_foot").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_lower_foot, this)
            })
            it.simples.add(Line(PointF(3.5F,7.5F), PointF(2.5F,10.5F), paint,"left_upper_foot").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_upper_foot, this)
            })
            it.simples.add(Line(PointF(2.6F,10F), PointF(2.5F,12F), paint,"left_lower_foot").also {
                    line ->  line.setVectorDrawable(R.drawable.ic_lower_foot, this)
            })

            it.simples.add(Rectangle(PointF(3F,4.5F), PointF(5F,8F), paint,"body").also {
                    rectangle ->  rectangle.setVectorDrawable(R.drawable.ic_torso, this)
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
