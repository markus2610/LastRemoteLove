package com.meoyawn.remotelove.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import rx.Subscriber
import rx.Observable
import rx.subscriptions.Subscriptions
import rx.lang.kotlin.asObservable

/**
 * Created by adelnizamutdinov on 10/3/14
 */
fun Animator.ends(): Observable<Animator> = {(subscriber: Subscriber<in Animator>) ->
  val listener = object : AnimatorListenerAdapter() {
    override fun onAnimationEnd(animation: Animator) {
      subscriber.onNext(animation)
      subscriber.onCompleted()
    }
  }
  subscriber.add(Subscriptions.create { this.removeListener(listener) })
  this.addListener(listener);
}.asObservable()