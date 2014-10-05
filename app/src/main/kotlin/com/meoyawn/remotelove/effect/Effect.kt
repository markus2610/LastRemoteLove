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

data class Loading<T>(val loading: Boolean) : Effect<T> {
  class object {
    fun wrap<T>(o: Observable<T>): Observable<Effect<T>> = wrapEffect(Effect.wrap(o))

    fun wrapEffect<T>(source: Observable<Effect<T>>): Observable<Effect<T>> =
        Observable.concat(Observable.just(Loading(true)),
                          source,
                          Observable.just(Loading(false)))!!
  }
}

data class Progress<T>(val percent: Int) : Effect<T> {
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

data class Success<T>(val value: T) : Effect<T> {
  class object {
    fun from<T>(t: T): Effect<T> = Success(t)
  }
}

data class Failure<T>(val throwable: Throwable) : Effect<T> {
  class object {
    fun from<T>(t: Throwable): Effect<T> = Failure(t)
  }
}