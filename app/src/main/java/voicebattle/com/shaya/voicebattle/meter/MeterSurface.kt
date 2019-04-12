package voicebattle.com.shaya.voicebattle.meter

import android.graphics.*
import android.view.SurfaceHolder
import android.view.SurfaceView
import io.reactivex.disposables.CompositeDisposable
import voicebattle.com.shaya.voicebattle.MainActivity
import voicebattle.com.shaya.voicebattle.Store
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

    private fun drawText(canvas: Canvas,displaySize: Point, volume: Int){
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 100F
        }
        val ( x, y ) = Pair(displaySize.x, displaySize.y)
        canvas.drawText(volume.toString(),x/2.toFloat(),y/4.toFloat(),paint)
    }

    private fun drawMeter(holder: SurfaceHolder,volume: Int){
        holder.lockCanvas()?.let {canvas ->
            canvas.drawColor(Color.WHITE)
            size?.let{
                val strokeWidth = decideDetect(it)
                val paint = Paint().apply {
                    this.color = Color.GREEN
                    this.strokeWidth = strokeWidth
                    this.style = Paint.Style.STROKE
                }
                val r = decideMeterR(it,strokeWidth)
                drawText(canvas,it,volume)
                drawCircleOnCircleTrajectory(canvas,it,paint,r, (volume).toFloat() / maxVolume * 100)
                drawCircleOnDisplayCenter(canvas,it,paint,r,(volume).toFloat() / maxVolume * 100)
            }

            holder.unlockCanvasAndPost(canvas)
        }
    }
    private fun decideDetect(displaySize:Point):Float{
        val ratio = 8
        val ( x, y ) = Pair(displaySize.x, displaySize.y)
        return if( x > y ) ( y / ratio ).toFloat() else ( x / ratio ).toFloat()
    }
    private fun decideMeterR(displaySize: Point,strokeWidth:Float):Float{
        val baseSize = if(displaySize.x > displaySize.y) displaySize.y else displaySize.x
        return  (baseSize - strokeWidth) / 2
    }

    private fun drawCircleOnDisplayCenter(canvas: Canvas, displaySize: Point, paint: Paint, r:Float,percent:Float){
        val ( positionOfDrawX, positionOfDrawY ) = calcCirclePosition(displaySize)
        val rect = RectF(positionOfDrawX-r,positionOfDrawY-r,positionOfDrawX+r,positionOfDrawY+r)

        canvas.drawArc(rect,180f,180f * percent /100,false,paint)
    }
    private fun calcCirclePosition(displaySize: Point):Pair<Int,Int>{
        val ( x, y ) = Pair(displaySize.x, displaySize.y)
        return Pair(x/2,y*2/3)
    }

    private fun drawCircleOnCircleTrajectory(canvas: Canvas, displaySize: Point, paint:Paint, r : Float, percent: Float){
        val ( positionOfDrawX, positionOfDrawY ) = calcCirclePosition(displaySize)
        val (x,y) = calcMetorPoint(percent,r,positionOfDrawX.toFloat(),positionOfDrawY.toFloat())
        canvas.drawCircle(x.toFloat(),y.toFloat(),10f,paint)
    }
    private fun calcMetorPoint(percent:Float,r:Float,originX:Float,originY: Float): Pair<Double, Double> {
        val theta = Math.PI * ( 1 - percent / 100)
        val x = originX + r * Math.cos(theta)
        val y = originY - r * sin(theta)
        return Pair(x,y)
    }
}