package com.meoyawn.remotelove.widgets

import rx.Observable
import rx.subjects.PublishSubject
import android.widget.TextView
import android.animation.ObjectAnimator
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import rx.Subscriber
import rx.lang.kotlin.asObservable
import rx.subscriptions.Subscriptions

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

fun ObjectAnimator.ends(): Observable<Animator> = {(subscriber: Subscriber<in Animator>) ->
    val listener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            subscriber.onNext(animation)
            subscriber.onCompleted()
        }
    }
    subscriber.add(Subscriptions.create { this.removeListener(listener) })
    this.addListener(listener);
}.asObservable()