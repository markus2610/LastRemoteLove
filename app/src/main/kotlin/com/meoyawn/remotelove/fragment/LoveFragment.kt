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
import android.widget.PopupMenu
import com.readystatesoftware.systembartint.SystemBarTintManager
import com.meoyawn.remotelove.widget.Views

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoveEffect(val loved: Boolean) : Effect<Status>

class LoveFragment : LoveFragmentBase() {
  var track: Track = Track.EMPTY
  var localLoved: Boolean? = null

  override fun onViewCreated(view: View?, inState: Bundle?) {
    super<LoveFragmentBase>.onViewCreated(view, inState)
    Dagger.inject(this)
    ButterKnife.inject(this, view)

    val cfg = SystemBarTintManager(getActivity()).getConfig()!!;
    mainFrame.setPadding(0,
                         0,
                         cfg.getPixelInsetRight(),
                         cfg.getPixelInsetBottom());

    if (inState != null) {
      track = inState.getParcelable("track")!!;
      localLoved = inState.getBoolean("localLoved")
      draw(track)
      if (localLoved != null) {
        draw(localLoved!!)
      }
    }

    loveButtonPipe()
    moreButtonPipe()
    loveResultsPipe()
    tracksResultsPipe()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super<LoveFragmentBase>.onSaveInstanceState(outState)
    outState.putParcelable("track", track)
    if (localLoved != null) {
      outState.putBoolean("localLoved", localLoved!!)
    }
  }

  fun tracksResultsPipe() {
    tracksSubject
        .takeUntil(viewDestroys)!!
        .observeOn(AndroidSchedulers.mainThread())!!
        .subscribe {
          when (it) {
            is Loading -> {
              if (it.loading && track == Track.EMPTY) {
                love.setVisibility(View.INVISIBLE)
              }
            }
            is Failure -> {
              Views.setInvisible(progressBar, albumImage, love, artist)
              title.setText(R.string.check_network_connection)
            }
            is Success -> draw(it.value)
          }
        }
  }

  fun loveResultsPipe() {
    statusSubject
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

  fun moreButtonPipe() {
    more.clicks()
        .takeUntil(viewDestroys)!!
        .subscribe {
          val pm = PopupMenu(getActivity()!!, more)
          pm.inflate(R.menu.love_more)
          pm.setOnMenuItemClickListener {
            when (it?.getItemId()) {
              R.id.logout -> {
                Timber.d("logging out")
                preferencesLazy.get()!!.session(null)
                getFragmentManager()!!.beginTransaction()
                    .replace(android.R.id.content, LoginFragment())
                    .commit()
                true
              }
              else -> false
            }
          }
          pm.show()
        }
  }

  fun loveButtonPipe() {
    val albumTaps = albumImage.clicks()
    val doubleTaps = albumTaps.flatMap { albumTaps.takeUntil(Observable.timer(200, TimeUnit.MILLISECONDS)) }

    val clicks = Observable.merge(love.clicks(), doubleTaps)!!
    val sub = clicks.flatMap {
      val sk = preferencesLazy.get()?.session()?.key
      val artist = track.artist.name
      val name = track.name
      if (sk != null ) {
        val loved = localLoved ?: track.loved != 0
        val method = if (loved) TRACK_UNLOVE else TRACK_LOVE
        val sig = apiSig(API_KEY, artist, method, sk, name, API_SECRET)
        Effect.wrap(lastFmLazy.get()!!.loveUnlove(API_KEY, sig, artist, method, sk, name)
                        ?.subscribeOn(Schedulers.io())!!)
            .startWith(LoveEffect(!loved))!!
            .takeUntil(clicks)!!
      } else {
        Observable.empty<Effect<Status>>()
      }
    }!!.subscribe(statusSubject)
    destroys.subscribe { sub?.unsubscribe() }
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
      val sub = Observable.interval(10, TimeUnit.SECONDS, Schedulers.io())!!
          .startWith(-1L)!!
          .flatMap {
            Loading.wrap(
                lastFmLazy.get()!!
                    .getRecentTracks(session.name, API_KEY)!!
                    .map {
                      val tracks = it!!.recenttracks.track
                      if (tracks.size == 2) tracks.first() else Track.EMPTY
                    }!!)
          }!!
          .subscribe(tracksSubject)
      pauses.subscribe { sub?.unsubscribe() }
    }
  }

  fun draw(t: Track) {
    this.track = t;
    if (t == Track.EMPTY) {
      Views.setInvisible(progressBar, albumImage, love, artist)
      title.setText(R.string.nothing_is_playing)
    } else {
      Views.setVisible(progressBar, albumImage, love, artist)

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