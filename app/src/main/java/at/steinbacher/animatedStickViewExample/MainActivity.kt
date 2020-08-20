package at.steinbacher.animatedStickViewExample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import at.steinbacher.android_animated_stick_view.*
import at.steinbacher.animatedStickViewExample.demos.HighKnees

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

        /*
        demo scene
        [{"item_type":"Grid","item":{"paint":{"color":"#FFC3C3C3"},"tag":"grid"}},{"item_type":"Rectangle","item":{"topLeft":{"x":3.7,"y":3.7},"bottomRight":{"x":4.3,"y":5},"paint":{"color":"#FF000000"},"drawable":2131099748,"tag":"neck"}},{"item_type":"Rectangle","item":{"topLeft":{"x":3.2,"y":2.5},"bottomRight":{"x":4.8,"y":4.3},"paint":{"color":"#FF000000"},"drawable":2131099743,"tag":"head"}},{"item_type":"Line","item":{"startPoint":{"x":4.9,"y":4.8},"endPoint":{"x":5.592743,"y":7.002093},"width":0.6,"paint":{"color":"#FF000000"},"tag":"right_upper_arm"}},{"item_type":"Line","item":{"startPoint":{"x":5.6,"y":6.7},"endPoint":{"x":5.6,"y":8.9},"width":0.4,"paint":{"color":"#FF000000"},"tag":"right_lower_arm"}},{"item_type":"Line","item":{"startPoint":{"x":3.1,"y":4.8},"endPoint":{"x":2.5,"y":7},"width":0.6,"paint":{"color":"#FF000000"},"tag":"left_upper_arm"}},{"item_type":"Line","item":{"startPoint":{"x":2.52,"y":6.7},"endPoint":{"x":2.5,"y":8.9},"width":0.4,"paint":{"color":"#FF000000"},"tag":"left_lower_arm"}},{"item_type":"Line","item":{"startPoint":{"x":4.5185184,"y":7.459803},"endPoint":{"x":5.5185184,"y":10.459802},"width":0.9,"paint":{"color":"#FF000000"},"tag":"right_upper_foot"}},{"item_type":"Line","item":{"startPoint":{"x":5.4,"y":10},"endPoint":{"x":5.5,"y":12},"width":0.6,"paint":{"color":"#FF000000"},"tag":"right_lower_foot"}},{"item_type":"Line","item":{"startPoint":{"x":3.5,"y":7.5},"endPoint":{"x":2.5,"y":10.5},"width":0.9,"paint":{"color":"#FF000000"},"tag":"left_upper_foot"}},{"item_type":"Line","item":{"startPoint":{"x":2.6,"y":10},"endPoint":{"x":2.5,"y":12},"width":0.6,"paint":{"color":"#FF000000"},"tag":"left_lower_foot"}},{"item_type":"Rectangle","item":{"topLeft":{"x":3,"y":4.5},"bottomRight":{"x":5,"y":8},"paint":{"color":"#FF000000"},"drawable":2131099749,"tag":"body"}}]
         */

        stickView?.let {
            it.setVerticalLinesCount(10)
            it.enableDynamicHorizontalLinesCount(true)
            it.setSceneCollection(HighKnees(this,
                R.drawable.ic_neck,
                R.drawable.ic_head,
                R.drawable.ic_torso,
                R.drawable.ic_upper_arm,
                R.drawable.ic_lower_arm,
                R.drawable.ic_upper_foot,
                R.drawable.ic_lower_foot,
                R.drawable.ic_shoe_front,
                R.drawable.ic_hand_front_left,
                R.drawable.ic_hand_front_right).sceneCollection)
            it.setAnimationDuration(700)
            it.startAnimation()
        }
    }
}
