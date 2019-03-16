package voicebattle.com.shaya.voicebattle

import android.graphics.*
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.math.sin

class MeterSurface(activity: MainActivity?,state:AudioStore) : SurfaceView(activity),SurfaceHolder.Callback{
    val size:Point?
    var maxVolume:Int = 2000
    var beforeVolume:Int=0
    init {
        super.getHolder().addCallback(this)
        size = activity?.windowManager?.defaultDisplay?.let {
            Point().apply {
                it.getSize(this)
            }
        }
        state.refreshValume.subscribe{volume->
            holder?.let {
                drawMeter(it,volume,beforeVolume,10)
                beforeVolume = volume
            }
        }
    }
    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        holder?.let {
                drawMeter(it,1000,beforeVolume,15)
        }
    }
    private fun drawMeter(holder: SurfaceHolder,volume:Int,beforeVolume:Int,speed:Int){
        GlobalScope.launch {
            (0..abs(volume-beforeVolume) step speed).forEach {
                holder.lockCanvas().apply {
                    Log.d("beforeV",it.toString())
                    val direction = if(volume > beforeVolume) 1 else -1
                    drawMeterForEach(this,beforeVolume,it * direction)
                    holder.unlockCanvasAndPost(this)
                }
            }
        }
    }
    private fun drawMeterForEach(canvas: Canvas,volume: Int,deltaVolume:Int){
        canvas.drawColor(Color.WHITE)
        val paint = Paint().apply {
            color = Color.GREEN
            strokeWidth = 150f
            style = Paint.Style.STROKE
        }
        size?.let{
            Log.d("volume","${(volume).toFloat()}")
            drawCircleOnCircleTrajectory(canvas,it,paint,500f, (volume+deltaVolume).toFloat() / maxVolume * 100)
            drawCircleOnDisplayCenter(canvas,it,500f,(volume+deltaVolume).toFloat() / maxVolume * 100,paint)
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