package voicebattle.com.shaya.voicebattle

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor

class Dispatcher {
    private val processor = PublishProcessor.create<Action>().toSerialized()
    fun dispatch(action: Action) {
        processor.onNext(action)
    }
    fun <T : Action> on(clazz: Class<T>): Observable<T> {
        return processor.onBackpressureBuffer()
                .ofType(clazz)
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
    }
}