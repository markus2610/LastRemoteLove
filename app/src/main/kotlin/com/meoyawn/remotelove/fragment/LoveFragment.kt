package com.meoyawn.remotelove.fragment

import com.meoyawn.remotelove.LoveFragmentBase
import android.os.Bundle
import rx.Observable
import java.util.concurrent.TimeUnit
import com.meoyawn.remotelove.Dagger
import com.meoyawn.remotelove.api.API_KEY
import rx.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoveFragment : LoveFragmentBase() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super<LoveFragmentBase>.onCreate(savedInstanceState)
    Dagger.inject(this)
    Observable.interval(1, TimeUnit.SECONDS)!!
        .flatMap {
          val session = preferencesLazy.get()?.session()
          lastFmLazy.get()?.getRecentTracks(session?.name as String, API_KEY)!!
              .subscribeOn(Schedulers.io())
        }!!
        .map { it?.recenttracks?.track?.filter { it.attr?.nowplaying }?.first }!!
        .takeUntil(viewDestroys)!!
        .subscribe { Timber.d("%s", it) }
  }
}