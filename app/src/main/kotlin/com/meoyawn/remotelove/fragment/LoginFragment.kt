package com.meoyawn.remotelove.fragment

import com.meoyawn.remotelove.LoginFragmentBase
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.meoyawn.remotelove.R
import com.meoyawn.remotelove.widget.submits
import android.widget.EditText
import android.text.TextUtils
import rx.Observable
import butterknife.ButterKnife
import com.meoyawn.remotelove.HandleBack
import kotlin.properties.Delegates
import android.widget.ProgressBar
import com.meoyawn.remotelove.widget.translate
import com.meoyawn.remotelove.api.apiSig
import com.meoyawn.remotelove.api.GET_MOBILE_SESSION
import rx.schedulers.Schedulers
import com.meoyawn.remotelove.Dagger
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoginFragment : LoginFragmentBase(), HandleBack {
  val username: EditText by Delegates.lazy { usernameStub.inflate() as EditText }
  val password: EditText by Delegates.lazy { passwordStub.inflate() as EditText }
  val progress: ProgressBar by Delegates.lazy { progressStub.inflate() as ProgressBar }
  val translate: (View, View, Boolean) -> Unit by Delegates.lazy { translate(getView()) }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
      inflater.inflate(R.layout.login, container, false)

  override fun onCreate(savedInstanceState: Bundle?) {
    super<LoginFragmentBase>.onCreate(savedInstanceState)
    viewCreates
        .flatMap { view ->
          Dagger.inject(this)
          ButterKnife.inject(this, view)
          username.submits()
              .flatMap { usrnm ->
                if (TextUtils.isEmpty(usrnm))
                  Observable.empty<String>()
                else {
                  translate(username, password, true)
                  password.submits()
                      .flatMap { pwd ->
                        if (TextUtils.isEmpty(pwd)) {
                          Observable.empty<String>()
                        } else {
                          translate(password, progress, true)

                          val API_KEY = "59ce954b080ef3eb99cca836896dbf5e"
                          val uas = usrnm as String
                          val pas = pwd as String
                          val apiSig = apiSig(API_KEY, GET_MOBILE_SESSION, pas, uas, "d4c1fab919d52f46fd1d2829a37d127c")
                          lastFmLazy.get()
                              ?.getMobileSession(pas, uas, API_KEY, apiSig)!!
                              .subscribeOn(Schedulers.io())!!
                              .doOnNext { preferencesLazy.get()?.session(it?.session) }
                        }
                      }
                }
              }
        }!!
        .takeUntil(viewDestroys)!!
        .observeOn(AndroidSchedulers.mainThread())!!
        .subscribe {
          getFragmentManager()!!.beginTransaction()
              .replace(android.R.id.content, LoveFragment())
              .commit()
        }
  }

  override fun handleBack(): Boolean = when {
    progress.getVisibility() == View.VISIBLE -> {
      translate(progress, password, false)
      true
    }
    password.getVisibility() == View.VISIBLE -> {
      translate(password, username, false)
      true
    }
    else                                     -> false
  }
}