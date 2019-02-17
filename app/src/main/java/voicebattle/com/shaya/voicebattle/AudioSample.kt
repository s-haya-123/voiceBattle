package voicebattle.com.shaya.voicebattle

import android.media.*
import android.util.Log
import kotlin.math.max
import java.nio.file.Files.createFile
import android.media.AudioFormat.CHANNEL_OUT_STEREO
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioAttributes
import android.media.AudioTrack
import kotlin.math.abs


/**
 * AudioRecord クラスのサンプルコード
 */
class AudioSample(val graphSample: GraphSample) {

    var audioRecord: AudioRecord //録音用のオーディオレコードクラス
    val SAMPLING_RATE = 44100 //オーディオレコード用サンプリング周波数
    private var bufSize: Int//オーディオレコード用バッファのサイズ
    private var shortData: ShortArray //オーディオレコード用バッファ
    private  var playShortData:ShortArray
    private val loopcnt = 500
    private  val framerate = 2
    private val oneFrameDataCount:Int  //SAMPLING_RATE / framerate
//    private val oneFrameSizeInByte = oneFrameDataCount * 2

    //AudioRecordの初期化
    init {
        // AudioRecordオブジェクトを作成
        bufSize = max(1 * 10,AudioRecord.getMinBufferSize(SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT))
        oneFrameDataCount = bufSize/2
        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufSize)

        shortData = ShortArray(oneFrameDataCount)
        playShortData = ShortArray(oneFrameDataCount * loopcnt)
        var cnt =0
        // コールバックを指定
        audioRecord.setRecordPositionUpdateListener(object : AudioRecord.OnRecordPositionUpdateListener {
            // フレームごとの処理
            override fun onPeriodicNotification(recorder: AudioRecord) {
                val max = shortData.max()!!.toFloat()
                val min = shortData.min()!!.toFloat()
                val values = if(abs(max) > abs(min)) max else min
                graphSample.addGraph(values)
                System.arraycopy(shortData,0,playShortData,cnt * oneFrameDataCount,oneFrameDataCount)
                audioRecord.read(shortData, 0, oneFrameDataCount) // 読み込む
                cnt++
                Log.d("sound",cnt.toString())
            }

            override fun onMarkerReached(recorder: AudioRecord) {
                Log.d("sound","marker")

            }
        })
        // コールバックが呼ばれる間隔を指定
        audioRecord.positionNotificationPeriod = oneFrameDataCount
        Log.d("sound","${AudioRecord.getMinBufferSize(SAMPLING_RATE,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT)}, ${oneFrameDataCount}")
    }
    fun playAudioRecord(){
        val player = AudioTrack.Builder()
                .setAudioAttributes(AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .setAudioFormat(AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(SAMPLING_RATE)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build())
                .setTransferMode(AudioTrack.MODE_STREAM)
                .setBufferSizeInBytes(bufSize/framerate)
                .build()
//        player.reloadStaticData()
        player.setPlaybackRate(SAMPLING_RATE)
        player.play()
        player.write(playShortData, 0, playShortData.size)
        player.stop()
        player.flush()
    }


    fun startAudioRecord() {
        audioRecord.startRecording()
        audioRecord.read(shortData, 0, oneFrameDataCount)
//        (0..loopcnt-1).forEach {
//            audioRecord.read(shortData!!, 0, bufSize / 2)
//            System.arraycopy(shortData,0,playShortData,it * bufSize / 2,bufSize / 2)
//            Log.d("sound",shortData!!.map { abs(it.toInt()) }.max().toString())
//        }
//
//        audioRecord.stop()
        Log.d("sound","end.")
    }

    //オーディオレコードを停止する
    fun stopAudioRecord() {
        audioRecord.stop()
    }
}