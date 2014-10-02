package com.meoyawn.remotelove.widget

import rx.Observable
import rx.subjects.PublishSubject
import android.widget.TextView

/**
 * Created by adelnizamutdinov on 10/1/14
 */
fun TextView.submits(): Observable<String> {
    val s = PublishSubject.create<String>() as PublishSubject<String>
    this.setOnEditorActionListener {(textView, i, keyEvent) ->
        s.onNext(textView?.getText().toString())
        true
    }
    return s
}