package com.meoyawn.remotelove.fragment

import android.os.Bundle
import com.meoyawn.remotelove.Dagger
import timber.log.Timber
import android.view.View
import butterknife.ButterKnife
import android.text.TextUtils
import com.meoyawn.remotelove.R
import com.meoyawn.remotelove.api.model.Track
import com.meoyawn.remotelove.effect.Loading
import com.meoyawn.remotelove.effect.Success
import com.meoyawn.remotelove.effect.Failure
import rx.Observable
import java.util.concurrent.TimeUnit
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers
import com.meoyawn.remotelove.widget.clicks
import com.meoyawn.remotelove.api.API_KEY
import com.meoyawn.remotelove.api.API_SECRET
import com.meoyawn.remotelove.api.apiSig
import com.meoyawn.remotelove.api.TRACK_UNLOVE
import com.meoyawn.remotelove.api.TRACK_LOVE
import com.meoyawn.remotelove.effect.Effect
import com.meoyawn.remotelove.api.model.Status
import android.graphics.PorterDuff.Mode

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoveEffect(val loved: Boolean) : Effect<Status>

class LoveFragment : LoveFragmentBase() {
  var track: Track = Track.EMPTY
  var localLoved: Boolean? = null

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super<LoveFragmentBase>.onViewCreated(view, savedInstanceState)
    Dagger.inject(this)
    ButterKnife.inject(this, view)
    val clicks = love.clicks()
    clicks
        .flatMap {
          val sk = preferencesLazy.get()?.session()?.key
          val artist = track.artist.name
          val name = track.name
          if (sk != null ) {
            val loved = localLoved ?: track.loved != 0
            Timber.d("track is %b", loved)
            val method = if (loved) TRACK_UNLOVE else TRACK_LOVE
            Timber.d("will %s", method)
            val sig = apiSig(API_KEY, artist, method, sk, name, API_SECRET)
            Effect.wrap(lastFmLazy.get()!!.loveUnlove(API_KEY, sig, artist, method, sk, name)
                            ?.subscribeOn(Schedulers.io())!!
                            .takeUntil(clicks)!!)
                .startWith(LoveEffect(!loved))!!
          } else {
            Observable.empty<Effect<Status>>()
          }
        }!!
        .takeUntil(destroys)!!
        .subscribe(statusSubject)

    statusSubject
        .asObservable()!!
        .takeUntil(viewDestroys)!!
        .observeOn(AndroidSchedulers.mainThread())!!
        .subscribe {
          when (it) {
            is Failure -> {
              if (localLoved != null) {
                Timber.e(it.throwable, "failure, persisting %b", !localLoved!!)
                track.loved = if (localLoved!!) 0 else 1
                localLoved = null
              }
              draw(track.loved != 0)
            }
            is Success -> {
              if (localLoved != null) {
                Timber.d("success, persisting %b", localLoved)
                track.loved = if (localLoved!!) 1 else 0
                localLoved = null
              }
            }
            is LoveEffect -> {
              Timber.d("setting local as %b", it.loved)
              localLoved = it.loved
              draw(it.loved)
            }
          }
        }
  }

  fun draw(loved: Boolean) {
    if (loved) {
      val mutated = getResources()?.getDrawable(R.drawable.ic_action_love)?.mutate()
      mutated?.setColorFilter(getResources()?.getColor(android.R.color.holo_red_light)!!, Mode.SRC_ATOP)
      love.setImageDrawable(mutated)
    } else {
      love.setImageResource(R.drawable.ic_action_love)
    }
  }

  override fun onResume() {
    super<LoveFragmentBase>.onResume()
    val session = preferencesLazy.get()?.session()
    if (session == null) {
      getFragmentManager()?.beginTransaction()
          ?.replace(android.R.id.content, LoginFragment())
          ?.commit()
    } else {
      Observable.interval(10, TimeUnit.SECONDS, Schedulers.io())!!
          .startWith(-1L)!!
          .flatMap {
            Loading.wrap(
                lastFmLazy.get()!!
                    .getRecentTracks(session.name, API_KEY)!!
                    .map {
                      val arrayOfTracks = it!!.recenttracks.track
                      val filtered = arrayOfTracks.filter { it.attr?.nowplaying ?: false }
                      filtered.first ?: Track.EMPTY
                    }!!)
          }!!
          .takeUntil(pauses)!!
          .observeOn(AndroidSchedulers.mainThread())!!
          .subscribe {
            when (it) {
              is Loading -> {
                Timber.d("%s", it)
              }
              is Failure -> {
                title.setText(R.string.check_network_connection)
                coverFrame.setVisibility(View.INVISIBLE)
                love.setVisibility(View.GONE)
                artist.setVisibility(View.INVISIBLE)
              }
              is Success -> draw(it.value)
            }
          }
    }
  }

  fun draw(t: Track) {
    this.track = t;
    if (t == Track.EMPTY) {
      // TODO no track
      coverFrame.setVisibility(View.INVISIBLE)
      love.setVisibility(View.GONE)
      artist.setVisibility(View.INVISIBLE)
    } else {
      coverFrame.setVisibility(View.VISIBLE)
      love.setVisibility(View.VISIBLE)
      artist.setVisibility(View.VISIBLE)

      artist.setText(t.artist.name)
      title.setText(t.name)

      draw(t.loved != 0)

      val albumCover = t.image.lastOrNull()?.url
      val image = when {
        TextUtils.isEmpty(albumCover) -> t.artist.image.lastOrNull()?.url
        else -> albumCover
      }
      if (!TextUtils.isEmpty(image)) {
        picassoLazy.get()!!.load(image)!!.into(albumImage)
      } else {
        albumImage.setImageResource(R.drawable.lastfm)
      }
    }
  }
}