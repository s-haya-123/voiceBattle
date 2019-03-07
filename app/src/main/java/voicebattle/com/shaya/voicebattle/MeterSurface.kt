package voicebattle.com.shaya.voicebattle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.sin

class MeterSurface(activity: MainActivity?) : SurfaceView(activity),SurfaceHolder.Callback{
    val point:Point?
    init {
        super.getHolder().addCallback(this)
        point = activity?.windowManager?.defaultDisplay?.let {
            Point().apply {
                it.getSize(this)
            }
        }
    }
    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {

        holder?.let {
            val canvas = holder.lockCanvas().apply {
                drawColor(Color.WHITE)
                val paint = Paint().apply {
                    color = Color.GREEN
                    strokeWidth = 10f
                    style = Paint.Style.STROKE
                }
                point?.let{
                    drawCircle(it.x /2.toFloat(),it.y/2.toFloat(),10f,paint)
                    drawCircleOnCircleTrajectory(this,it,paint,0f)
                    drawCircleOnCircleTrajectory(this,it,paint,20f)
                    drawCircleOnCircleTrajectory(this,it,paint,30f)
                }

            }
            holder.unlockCanvasAndPost(canvas);
        }
    }
    private fun drawCircleOnCircleTrajectory(canvas: Canvas,point: Point,paint:Paint,percent: Float){
        val (x,y) = calcMetorPoint(percent,300f,point.x/2.toFloat(),point.y/2.toFloat())
        canvas.drawCircle(x.toFloat(),y.toFloat(),10f,paint)
    }
    private fun calcMetorPoint(percent:Float,r:Float,originX:Float,originY: Float): Pair<Double, Double> {
        val theta = Math.PI * ( 1 - percent / 100)
        val x = originX + r * Math.cos(theta)
        val y = originY - r * sin(theta)
        return Pair(x,y)
    }
}