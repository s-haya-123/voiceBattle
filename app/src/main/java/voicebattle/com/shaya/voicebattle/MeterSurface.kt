package voicebattle.com.shaya.voicebattle

import android.content.Context
import android.graphics.Color
import android.view.SurfaceHolder
import android.view.SurfaceView

class MeterSurface(context: Context?) : SurfaceView(context),SurfaceHolder.Callback{
    init {
        super.getHolder().addCallback(this)
    }
    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        holder?.let {
            val canvas = holder.lockCanvas().apply {
                drawColor(Color.WHITE)
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }
}