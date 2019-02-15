package voicebattle.com.shaya.voicebattle

import android.media.*
import android.util.Log
import kotlin.math.max
import java.nio.file.Files.createFile
import android.media.AudioFormat.CHANNEL_OUT_STEREO
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioAttributes
import android.media.AudioTrack





/**
 * AudioRecord クラスのサンプルコード
 */
class AudioSample {

    var audioRecord: AudioRecord //録音用のオーディオレコードクラス
    val SAMPLING_RATE = 44100 //オーディオレコード用サンプリング周波数
    private var bufSize: Int = 0//オーディオレコード用バッファのサイズ
    private var shortData: ByteArray? = null //オーディオレコード用バッファ
    private  var playShortData:ByteArray
    private val loopcnt = 100

    //AudioRecordの初期化
    init {
        // AudioRecordオブジェクトを作成
        bufSize = android.media.AudioRecord.getMinBufferSize(SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT)
        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufSize)

        shortData = ByteArray(bufSize / 2)
        playShortData = ByteArray(bufSize / 2 * loopcnt)
//        var cnt =0
//        // コールバックを指定
//        audioRecord.setRecordPositionUpdateListener(object : AudioRecord.OnRecordPositionUpdateListener {
//            // フレームごとの処理
//            override fun onPeriodicNotification(recorder: AudioRecord) {
//                // TODO Auto-generated method stub
//                System.arraycopy(shortData,0,playShortData,cnt * bufSize / 2,bufSize / 2)
//                audioRecord.read(shortData!!, 0, bufSize / 2) // 読み込む
//                cnt++
//                Log.d("sound",cnt.toString())
//            }
//
//            override fun onMarkerReached(recorder: AudioRecord) {
//                // TODO Auto-generated method stub
//                Log.d("sound","marker")
//
//            }
//        })
//        // コールバックが呼ばれる間隔を指定
//        audioRecord.positionNotificationPeriod = bufSize / 2
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
                .setBufferSizeInBytes(bufSize/2)
                .build()
//        player.reloadStaticData()
        player.setPlaybackRate(SAMPLING_RATE)
        player.play()
        player.write(playShortData, 0, playShortData!!.size)
        player.stop()
        player.flush()
    }


    fun startAudioRecord() {
        audioRecord.startRecording()
        (0..loopcnt-1).forEach {
            audioRecord.read(shortData!!, 0, bufSize / 2)
            System.arraycopy(shortData,0,playShortData,it * bufSize / 2,bufSize / 2)
            audioRecord.read(shortData!!, 0, bufSize / 2) // 読み込む
        }

        audioRecord.stop()
        Log.d("sound","end.")
    }

    //オーディオレコードを停止する
    fun stopAudioRecord() {
        audioRecord.stop()
    }
}