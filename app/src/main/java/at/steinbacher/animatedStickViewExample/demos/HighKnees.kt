package at.steinbacher.animatedStickViewExample.demos

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.PointF
import at.steinbacher.android_animated_stick_view.*

class HighKnees(context: Context,
                neckDrawable: Int,
                headDrawable: Int,
                torsoDrawable: Int,
                upperArmDrawable: Int,
                lowerArmDrawable: Int,
                upperFootDrawable: Int,
                lowerFootDrawable: Int,
                shoeDrawable: Int
) {
    val paint = Paint().apply {
        color = context.getColor(R.color.black)
        strokeWidth = 5f
    }

    val gridPaint = Paint().apply {
        color = context.getColor(R.color.grey)
        strokeWidth = 1f
    }

    private val scene1 = Scene().also {
        it.simples.add(Grid(gridPaint, "grid"))

        it.simples.add(Rectangle(PointF(3.7F,3.7F), PointF(4.3F,5F), paint,"neck").also {
                line ->  line.setVectorDrawable(neckDrawable, context)
        })
        it.simples.add(Rectangle(PointF(3.2F,2.5F), PointF(4.8F,4.3F), paint,"head").also {
                circle ->  circle.setVectorDrawable(headDrawable, context)
        })
        it.simples.add(Rectangle(PointF(3F,4.5F), PointF(5F,8F), paint,"body").also {
                rectangle ->  rectangle.setVectorDrawable(torsoDrawable, context)
        })
        it.simples.add(Line(PointF(4.9F,4.8F), PointF(5.4F,7F), 0.6f, paint,"right_upper_arm").also {
                line ->  line.setVectorDrawable(upperArmDrawable, context)
        })
        it.simples.add(Line(PointF(5.4F,6.7F), PointF(4.4F,5.4F), 0.4f, paint,"right_lower_arm").also {
                line ->  line.setVectorDrawable(lowerArmDrawable, context)
        })
        it.simples.add(Line(PointF(3.1F,4.8F), PointF(2.5F,7F), 0.6f, paint,"left_upper_arm").also {
                line ->  line.setVectorDrawable(upperArmDrawable, context)
        })
        it.simples.add(Line(PointF(2.52F,6.7F), PointF(3.5F,5.4F), 0.4f, paint,"left_lower_arm").also {
                line ->  line.setVectorDrawable(lowerArmDrawable, context)
        })


        it.simples.add(Line(PointF(4.5F,7.5F), PointF(4.6F,10F), 0.9f, paint,"right_upper_foot").also {
                line ->  line.setVectorDrawable(upperFootDrawable, context)
        })
        it.simples.add(Line(PointF(4.6F,9.5F), PointF(4.6F,12F), 0.6f, paint,"right_lower_foot").also {
                line ->  line.setVectorDrawable(lowerFootDrawable, context)
        })
        it.simples.add(Rectangle(PointF(4.3F, 11.4F), PointF(4.9F, 12F), paint, "right_shoe").also {
                rectangle ->  rectangle.setVectorDrawable(shoeDrawable, context)
        })

        it.simples.add(Line(PointF(3.5F,7.5F), PointF(3.4F,10F), 0.9f, paint,"left_upper_foot").also {
                line ->  line.setVectorDrawable(upperFootDrawable, context)
        })
        it.simples.add(Line(PointF(3.4F,9.5F), PointF(3.4F,12F), 0.6f, paint,"left_lower_foot").also {
                line ->  line.setVectorDrawable(lowerFootDrawable, context)
        })
        it.simples.add(Rectangle(PointF(3.1F, 11.4F), PointF(3.7F, 12F), paint, "left_shoe").also {
                rectangle ->  rectangle.setVectorDrawable(shoeDrawable, context)
        })
    }

    private val scene2 = Scene().also {
        //move up
        it.simples.add(Line(PointF(4.9F,4.8F), PointF(4.4F,6F), 0.6f, paint,"right_upper_arm"))
        it.simples.add(Line(PointF(4.4F,5.7F), PointF(4.4F,4.4F), 0.4f, paint,"right_lower_arm"))

        it.simples.add(Line(PointF(3.5F,7.5F), PointF(3.4F,5F), 0.9f, paint,"left_upper_foot"))
        it.simples.add(Line(PointF(3.4F,5.0F), PointF(3.4F,8F), 0.6f, paint,"left_lower_foot"))

        it.simples.add(Rectangle(PointF(3.1F, 7.4F), PointF(3.7F, 8F), paint, "left_shoe"))
    }

    private val scene3 = Scene().also {
        //move down
        it.simples.add(Line(PointF(4.9F,4.8F), PointF(5.4F,7F), 0.6f, paint,"right_upper_arm"))
        it.simples.add(Line(PointF(5.4F,6.7F), PointF(4.4F,5.4F), 0.4f, paint,"right_lower_arm"))

        it.simples.add(Line(PointF(3.5F,7.5F), PointF(3.4F,10F), 0.9f, paint,"left_upper_foot"))
        it.simples.add(Line(PointF(3.4F,9.5F), PointF(3.4F,12F), 0.6f, paint,"left_lower_foot"))

        it.simples.add(Rectangle(PointF(3.1F, 11.4F), PointF(3.7F, 12F), paint, "left_shoe"))
    }

    private val scene4 = Scene().also {
        //move up
        it.simples.add(Line(PointF(3.1F,4.8F), PointF(3.5F,6F), 0.6f, paint,"left_upper_arm"))
        it.simples.add(Line(PointF(3.5F,5.7F), PointF(3.5F,4.4F), 0.4f, paint,"left_lower_arm"))

        it.simples.add(Line(PointF(4.5F,7.5F), PointF(4.6F,5F), 0.9f, paint,"right_upper_foot"))
        it.simples.add(Line(PointF(4.6F,5.0F), PointF(4.6F,8F), 0.6f, paint,"right_lower_foot"))

        it.simples.add(Rectangle(PointF(4.3F, 7.4F), PointF(4.9F, 8F), paint, "right_shoe"))
    }

    private val scene5 = Scene().also {
        //move down
        it.simples.add(Line(PointF(3.1F,4.8F), PointF(2.5F,7F), 0.6f, paint,"left_upper_arm"))
        it.simples.add(Line(PointF(2.52F,6.7F), PointF(3.5F,5.4F), 0.4f, paint,"left_lower_arm"))

        it.simples.add(Line(PointF(4.5F,7.5F), PointF(4.6F,10F), 0.9f, paint,"right_upper_foot"))
        it.simples.add(Line(PointF(4.6F,9.5F), PointF(4.6F,12F), 0.6f, paint,"right_lower_foot"))

        it.simples.add(Rectangle(PointF(4.3F, 11.4F), PointF(4.9F, 12F), paint, "right_shoe"))
    }


    val sceneCollection = SceneCollection().apply {
        scenes.add(scene1)
        scenes.add(scene2)
        scenes.add(scene3)
        scenes.add(scene4)
        scenes.add(scene5)
    }
}