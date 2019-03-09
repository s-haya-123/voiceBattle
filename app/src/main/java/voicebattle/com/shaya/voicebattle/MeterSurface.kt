package voicebattle.com.shaya.voicebattle

import android.graphics.*
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
                    strokeWidth = 150f
                    style = Paint.Style.STROKE
                }
                point?.let{
//                    drawCircle(it.x /2.toFloat(),it.y/2.toFloat(),10f,paint)
                    drawCircleOnCircleTrajectory(this,it,paint,600f, 80f)
                    drawCircleOnDisplayCenter(this,it,600f,80f,paint)
                }

            }
            holder.unlockCanvasAndPost(canvas)
        }
    }
    private fun drawCircleOnDisplayCenter(canvas: Canvas, displaySize: Point, r:Float,percent:Float, paint: Paint){
        val centerX = (displaySize.x /2).toFloat()
        var centerY = (displaySize.y/2).toFloat()
        var rect = RectF(centerX-r,centerY-r,centerX+r,centerY+r)

        canvas.drawArc(rect,180f,180f * percent /100,false,paint)
    }

    private fun drawCircleOnCircleTrajectory(canvas: Canvas,point: Point,paint:Paint,r : Float, percent: Float){
        val (x,y) = calcMetorPoint(percent,r,point.x/2.toFloat(),point.y/2.toFloat())
        canvas.drawCircle(x.toFloat(),y.toFloat(),10f,paint)
    }
    private fun calcMetorPoint(percent:Float,r:Float,originX:Float,originY: Float): Pair<Double, Double> {
        val theta = Math.PI * ( 1 - percent / 100)
        val x = originX + r * Math.cos(theta)
        val y = originY - r * sin(theta)
        return Pair(x,y)
    }
}