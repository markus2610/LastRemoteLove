package com.meoyawn.remotelove.fragment

import android.os.Bundle
import android.view.View
import com.meoyawn.remotelove.widget.clicks
import butterknife.ButterKnife
import com.meoyawn.remotelove.Dagger
import com.meoyawn.remotelove.api.apiSig
import com.meoyawn.remotelove.api.API_KEY
import com.meoyawn.remotelove.api.API_SECRET
import rx.schedulers.Schedulers
import com.meoyawn.remotelove.widget.submits
import rx.Observable
import com.meoyawn.remotelove.effect.Progress
import rx.android.schedulers.AndroidSchedulers
import com.meoyawn.remotelove.effect.Failure
import com.meoyawn.remotelove.api.model.Session
import com.meoyawn.remotelove.R
import android.widget.Toast
import com.meoyawn.remotelove.effect.Success
import retrofit.RetrofitError
import com.meoyawn.remotelove.api.model.LastFmResponse
import com.meoyawn.remotelove.util.shortToast
import timber.log.Timber

data class ValueException<T>(val obj: T) : Exception()
data class ToastException(val stringRes: Int) : Exception()

class LoginFragment : LoginFragmentBase() {
  volatile var last: Toast? = null

  fun show(t: Toast?) {
    last?.cancel()
    last = t;
    t?.show()
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super<LoginFragmentBase>.onViewCreated(view, savedInstanceState)
    Dagger.inject(this)
    ButterKnife.inject(this, view)

    val logins = Observable.merge(password.submits(), submit.clicks())!!
        .flatMap {
          val pwd = password.getText().toString()
          val usrnm = username.getText().toString()
          val sig = apiSig(API_KEY, pwd, usrnm, API_SECRET)
          when {
            !usrnm.isEmpty() && !pwd.isEmpty() ->
              Progress.fakeWrap(
                  lastFmLazy.get()!!
                      .getMobileSession(pwd, usrnm, API_KEY, sig)!!
                      .flatMap {
                        val s = it?.session;
                        when (s) {
                          null -> Observable.error<Session>(ValueException(it))
                          else -> Observable.just(s)
                        }
                      }!!
                      .doOnNext { preferencesLazy.get()!!.session(it) }!!
                      .subscribeOn(Schedulers.io())!!)
                  .concatWith(Observable.just(Progress(0)))
            else                               -> Observable.just(Failure.from<Session>(ToastException(R.string.no_credentials)))
          }
        }!!
        .subscribe(subject)
    destroys.subscribe { logins?.unsubscribe() }

    subject
        .takeUntil(viewDestroys)!!
        .observeOn(AndroidSchedulers.mainThread())!!
        .subscribe({
          when (it) {
            is Progress -> {
              submit.setProgress(it.percent)

              val b = it.percent == 0
              username.setEnabled(b)
              password.setEnabled(b)
            }
            is Success  -> {
              if (preferencesLazy.get()!!.session() != null) {
                getFragmentManager()?.beginTransaction()
                    ?.replace(android.R.id.content, LoveFragment())
                    ?.commit()
              }
            }
            is Failure  -> {
              val t = it.throwable
              when (t) {
                is RetrofitError     -> when (t.getKind() == RetrofitError.Kind.NETWORK) {
                  true -> show(shortToast(R.string.check_network_connection))
                  else -> show(shortToast(R.string.unrecognized_error_occurred))
                }
                is ValueException<*> -> {
                  val sh: LastFmResponse = t.obj as LastFmResponse;
                  val m = sh.message!!
                  when (sh.error) {
                    4    -> when {
                      m.startsWith("Invalid password") -> show(shortToast(R.string.invalid_password))
                      m.startsWith("Invalid username") -> show(shortToast(R.string.invalid_username))
                      else                             -> show(shortToast(R.string.invalid_credentials))
                    }
                    else -> show(shortToast(R.string.invalid_credentials))
                  }
                }
                is ToastException    -> show(shortToast(t.stringRes))
                else                 -> show(shortToast(R.string.unrecognized_error_occurred))
              }
            }
          }
        }, {
          Timber.e(it, "")
        })
  }
}
