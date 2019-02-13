package voicebattle.com.shaya.voicebattle

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import kotlin.math.max
import java.nio.file.Files.createFile



/**
 * AudioRecord クラスのサンプルコード
 */
class AudioSample {

    var audioRecord: AudioRecord //録音用のオーディオレコードクラス
    val SAMPLING_RATE = 44100 //オーディオレコード用サンプリング周波数
    private var bufSize: Int = 0//オーディオレコード用バッファのサイズ
    private var shortData: ShortArray? = null //オーディオレコード用バッファ

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

        shortData = ShortArray(bufSize / 2)

        // コールバックを指定
        audioRecord.setRecordPositionUpdateListener(object : AudioRecord.OnRecordPositionUpdateListener {
            // フレームごとの処理
            override fun onPeriodicNotification(recorder: AudioRecord) {
                // TODO Auto-generated method stub
                audioRecord.read(shortData!!, 0, bufSize / 2) // 読み込む
            }

            override fun onMarkerReached(recorder: AudioRecord) {
                // TODO Auto-generated method stub

            }
        })
        // コールバックが呼ばれる間隔を指定
        audioRecord.positionNotificationPeriod = bufSize / 2
    }


    fun startAudioRecord() {
        audioRecord.startRecording()
        audioRecord.read(shortData!!, 0, bufSize / 2)
    }

    //オーディオレコードを停止する
    fun stopAudioRecord() {
        audioRecord.stop()
    }
}