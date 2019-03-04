package voicebattle.com.shaya.voicebattle

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.max

class AudioController @Inject constructor(private val audioActionCreator: AudioActionCreator) {
    val audioRecord: AudioRecord
    val oneFrameDataCount:Int
    val shortData:ShortArray


    init {
        val SAMPLING_RATE = 44100
        val bufSize = max(1 * 10,AudioRecord.getMinBufferSize(SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT))
        oneFrameDataCount = bufSize/2
        shortData = ShortArray(oneFrameDataCount)


        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufSize).apply {
            // コールバックを指定
            setRecordPositionUpdateListener(object : AudioRecord.OnRecordPositionUpdateListener {
                // フレームごとの処理
                override fun onPeriodicNotification(recorder: AudioRecord) {
                    val avarageVolume = shortData.map { abs(it.toFloat()) }.average()
                    audioActionCreator.updateMicVolume(avarageVolume.toInt())
                    this@apply.read(shortData, 0, oneFrameDataCount)
                }

                override fun onMarkerReached(recorder: AudioRecord) {

                }
            })
            // コールバックが呼ばれる間隔を指定
            positionNotificationPeriod = oneFrameDataCount
        }
    }
    fun startRecord(){
        audioRecord.startRecording()
        audioRecord.read(shortData, 0, oneFrameDataCount)
    }
    fun stopRecord(){
        audioRecord.stop()
    }


}