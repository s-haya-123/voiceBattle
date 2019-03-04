package voicebattle.com.shaya.voicebattle

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import java.util.*
import javax.inject.Inject

class Dispatcher {
    private val processor = PublishProcessor.create<AudioAction>().toSerialized()
    fun dispatch(action: AudioAction) {
        processor.onNext(action)
    }
    fun <T : AudioAction> on(clazz: Class<T>): Observable<T> {
        return processor.onBackpressureBuffer()
                .ofType(clazz)
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
    }
}