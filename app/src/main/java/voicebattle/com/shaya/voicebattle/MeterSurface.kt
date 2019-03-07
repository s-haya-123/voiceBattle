package voicebattle.com.shaya.voicebattle

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.SurfaceHolder
import android.view.SurfaceView

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
                }

            }
            holder.unlockCanvasAndPost(canvas);
        }
    }
}