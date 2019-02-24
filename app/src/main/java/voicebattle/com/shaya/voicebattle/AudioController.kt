package voicebattle.com.shaya.voicebattle

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import kotlin.math.abs
import kotlin.math.max

class AudioController {
    val audioRecord: AudioRecord
    init {
        val SAMPLING_RATE = 44100
        val bufSize = max(1 * 10,AudioRecord.getMinBufferSize(SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT))
        val oneFrameDataCount = bufSize/2


        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufSize).apply {
            val shortData = ShortArray(oneFrameDataCount)
            // コールバックを指定
            setRecordPositionUpdateListener(object : AudioRecord.OnRecordPositionUpdateListener {
                // フレームごとの処理
                override fun onPeriodicNotification(recorder: AudioRecord) {
                    this@apply.read(shortData, 0, oneFrameDataCount)
                }

                override fun onMarkerReached(recorder: AudioRecord) {

                }
            })
            // コールバックが呼ばれる間隔を指定
            positionNotificationPeriod = oneFrameDataCount
        }
    }
}