package com.meoyawn.remotelove.widget

import rx.Observable
import rx.subjects.PublishSubject
import android.widget.TextView
import timber.log.Timber
import android.view.KeyEvent

/**
 * Created by adelnizamutdinov on 10/1/14
 */
fun TextView.submits(): Observable<String> {
  val s = PublishSubject.create<String>() as PublishSubject<String>
  this.setOnEditorActionListener {(textView, i, keyEvent) ->
    Timber.d("clicking %s", keyEvent)
    if (keyEvent == null || keyEvent.getAction() == KeyEvent.ACTION_UP) {
      s.onNext(textView?.getText().toString())
    }
    true
  }
  return s
}