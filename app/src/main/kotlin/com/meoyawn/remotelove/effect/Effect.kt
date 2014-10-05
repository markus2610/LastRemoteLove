package com.meoyawn.remotelove.effect

import rx.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by adelnizamutdinov on 10/3/14
 */
trait Effect<T> {
  class object {
    fun wrap<T>(o: Observable<T>): Observable<Effect<T>> =
        o.map { Success.from(it!!) }!!.onErrorReturn { Failure.from(it!!) }!!
  }
}

enum class Loading<T> : Effect<T> {
  START
  END
}

object LoadingWrapper {
  fun from<T>(x: Loading<T>): Effect<T> = x

  fun wrap<T>(o: Observable<T>): Observable<Effect<in T>> = wrapEffect(Effect.wrap(o))

  fun wrapEffect<T>(o: Observable<Effect<T>>): Observable<Effect<in T>> =
      Observable.concat(Observable.just(from(Loading.START)), o, Observable.just(from(Loading.END)))!!
}

class Progress<T>(val percent: Int) : Effect<T> {
  class object {
    fun from<T>(percent: Int): Effect<T> = Progress(percent)

    fun fakeWrap<T>(o: Observable<T>): Observable<Effect<T>> {
      val cached = Effect.wrap(o.cache()!!)
      return Observable.interval(16, TimeUnit.MILLISECONDS)!!
          .map {
            when {
              it!! > 95 -> {
                val l = 95 + (it / 100)
                when {
                  l > 99 -> 99L
                  else -> l
                }
              }
              else -> it
            }
          }!!
          .map { Progress.from<T>(it!!.toInt()) }!!
          .takeUntil(cached)!!
          .concatWith(cached)!!
    }
  }
}

class Success<T>(val value: T) : Effect<T> {
  class object {
    fun from<T>(t: T): Effect<T> = Success(t)
  }
}

class Failure<T>(val throwable: Throwable) : Effect<T> {
  class object {
    fun from<T>(t: Throwable): Effect<T> = Failure(t)
  }
}