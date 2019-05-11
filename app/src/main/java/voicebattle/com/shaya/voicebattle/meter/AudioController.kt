package voicebattle.com.shaya.voicebattle.meter

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.max

class AudioController @Inject constructor(private val audioActionCreator: AudioActionCreator) {
    var audioRecord: AudioRecord?
    val oneFrameDataCount:Int
    val shortData:ShortArray
    val bufSize:Int
    val SAMPLING_RATE = 44100
    val audioVolumeScale = 10

    init {
        bufSize = max(1 * 10,AudioRecord.getMinBufferSize(SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT))
        oneFrameDataCount = bufSize/2
        shortData = ShortArray(oneFrameDataCount)
        audioRecord = null
        initAudioRecord()
    }
    fun initAudioRecord(){
        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufSize).apply {
            // コールバックを指定
            setRecordPositionUpdateListener(object : AudioRecord.OnRecordPositionUpdateListener {
                var cnt = 0
                var loopCnt = 10
                var playShortData = initPlayShortData(oneFrameDataCount,loopCnt)
                // フレームごとの処理
                override fun onPeriodicNotification(recorder: AudioRecord) {
                    System.arraycopy(shortData,0,playShortData,cnt * oneFrameDataCount,oneFrameDataCount)
                    if(loopCnt == cnt ){
                        val avarageVolume = playShortData.map { abs(it.toFloat()) }.average().toInt()
                        audioActionCreator.updateMicVolume(avarageVolume * audioVolumeScale)
                        playShortData = initPlayShortData(oneFrameDataCount,loopCnt)
                        cnt = 0
                    } else {
                        cnt++
                    }
                    this@apply.read(shortData, 0, oneFrameDataCount)

                }
                private fun initPlayShortData(frame:Int,loopCnt:Int):ShortArray{
                    return ShortArray(frame * (loopCnt+1))
                }
                override fun onMarkerReached(recorder: AudioRecord) {

                }
            })
            // コールバックが呼ばれる間隔を指定
            positionNotificationPeriod = oneFrameDataCount
        }
    }
    fun startRecord(){
        if(audioRecord?.state == AudioRecord.STATE_UNINITIALIZED){
            initAudioRecord()
        }
        audioRecord?.startRecording()
        audioRecord?.read(shortData, 0, oneFrameDataCount)
    }
    fun stopRecord(){
        audioRecord?.stop()
    }
}