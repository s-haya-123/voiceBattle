package voicebattle.com.shaya.voicebattle.meter

import android.graphics.*
import android.view.SurfaceHolder
import android.view.SurfaceView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import voicebattle.com.shaya.voicebattle.MainActivity
import voicebattle.com.shaya.voicebattle.Store
import kotlin.math.abs
import kotlin.math.sin

class MeterSurface(activity: MainActivity?, store: Store) : SurfaceView(activity),SurfaceHolder.Callback{
    val size:Point?
    var maxVolume:Int = 2000
    val compositeDisposable = CompositeDisposable()
    init {
        super.getHolder().addCallback(this)
        size = activity?.windowManager?.defaultDisplay?.let {
            Point().apply {
                it.getSize(this)
            }
        }
        store.refreshValume.subscribe{ volume->
            holder?.let {
                drawMeter(it,volume)
            }
        }.apply {
            compositeDisposable.add(this)
        }
    }
    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        compositeDisposable.clear()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        holder?.let {
            drawMeter(it,0)
        }
    }

    private fun drawMeter(holder: SurfaceHolder,volume: Int){
        holder.lockCanvas()?.let {canvas ->
            canvas.drawColor(Color.WHITE)
            val strokeWidth = 150f
            val paint = Paint().apply {
                this.color = Color.GREEN
                this.strokeWidth = strokeWidth
                this.style = Paint.Style.STROKE
            }

            size?.let{
                val r = decideMeterR(it,strokeWidth)
                drawCircleOnCircleTrajectory(canvas,it,paint,r, (volume).toFloat() / maxVolume * 100)
                drawCircleOnDisplayCenter(canvas,it,paint,r,(volume).toFloat() / maxVolume * 100)
            }

            holder.unlockCanvasAndPost(canvas)
        }

    }
    private fun drawMeter(holder: SurfaceHolder,volume:Int,beforeVolume:Int,speed:Int){
        GlobalScope.launch {
            (0..abs(volume-beforeVolume) step speed).forEach {
                holder.lockCanvas().apply {
                    val direction = if(volume > beforeVolume) 1 else -1
                    drawMeterForEach(this,beforeVolume,it * direction)
                    holder.unlockCanvasAndPost(this)
                }
            }
        }
    }
    private fun drawMeterForEach(canvas: Canvas,volume: Int,deltaVolume:Int){
        canvas.drawColor(Color.WHITE)
        val strokeWidth = 150f
        val paint = Paint().apply {
            this.color = Color.GREEN
            this.strokeWidth = strokeWidth
            this.style = Paint.Style.STROKE
        }

        size?.let{
            val r = decideMeterR(it,strokeWidth)
            drawCircleOnCircleTrajectory(canvas,it,paint,r, (volume+deltaVolume).toFloat() / maxVolume * 100)
            drawCircleOnDisplayCenter(canvas,it,paint,r,(volume+deltaVolume).toFloat() / maxVolume * 100)
        }
    }
    private fun decideMeterR(displaySize: Point,strokeWidth:Float):Float{
        val baseSize = if(displaySize.x > displaySize.y) displaySize.y else displaySize.x
        return  (baseSize - strokeWidth) / 2
    }

    private fun drawCircleOnDisplayCenter(canvas: Canvas, displaySize: Point, paint: Paint, r:Float,percent:Float){
        val centerX = (displaySize.x /2).toFloat()
        val centerY = (displaySize.y/2).toFloat()
        val rect = RectF(centerX-r,centerY-r,centerX+r,centerY+r)

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