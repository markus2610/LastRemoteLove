package com.meoyawn.remotelove.fragment

import com.meoyawn.remotelove.LoveFragmentBase
import android.os.Bundle
import rx.Observable
import java.util.concurrent.TimeUnit
import com.meoyawn.remotelove.Dagger
import com.meoyawn.remotelove.api.API_KEY
import rx.schedulers.Schedulers
import timber.log.Timber
import android.view.View
import butterknife.ButterKnife
import rx.android.schedulers.AndroidSchedulers
import android.text.TextUtils
import com.meoyawn.remotelove.R
import com.meoyawn.remotelove.api.model.Track
import com.meoyawn.remotelove.effect.Loading
import com.meoyawn.remotelove.effect.Success
import com.meoyawn.remotelove.effect.Failure

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoveFragment : LoveFragmentBase() {
  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super<LoveFragmentBase>.onViewCreated(view, savedInstanceState)
    Dagger.inject(this)
    ButterKnife.inject(this, view)

    val session = preferencesLazy.get()?.session()
    if (session == null) {
      getFragmentManager()?.beginTransaction()
          ?.replace(android.R.id.content, LoginFragment())
          ?.commit()
    } else {
      if (savedInstanceState == null) {
        Observable.interval(10, TimeUnit.SECONDS, Schedulers.io())!!
            .startWith(-1L)!!
            .flatMap {
              Loading.wrap(
                  lastFmLazy.get()!!
                      .getRecentTracks(session.name, API_KEY)!!
                      .map {
                        it!!.recenttracks.track.filter { it.attr?.nowplaying ?: false }.first
                            ?: Track.EMPTY
                      }!!)
            }!!
            .takeUntil(destroys)!!
            .subscribe(subject)
      }
    }
    subject
        .takeUntil(viewDestroys)!!
        .observeOn(AndroidSchedulers.mainThread())!!
        .subscribe {
          when (it) {
            is Loading -> {
              Timber.d("%s", it)
            }
            is Failure -> {

            }
            is Success -> {
              val t: Track = it.value
              artist.setText(t.artist.name)
              title.setText(t.name)

              val albumCover = t.image.lastOrNull()?.url
              val image = when {
                TextUtils.isEmpty(albumCover) -> t.artist.image.lastOrNull()?.url
                else -> albumCover
              }
              if (!TextUtils.isEmpty(image)) {
                Timber.d(image)
                picassoLazy.get()!!
                    .load(image)!!
                    .into(albumImage)
              } else {
                albumImage.setImageResource(R.drawable.lastfm)
              }
            }
          }
        }
  }
}