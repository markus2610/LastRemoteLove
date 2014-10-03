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
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.meoyawn.remotelove.util.ends
import butterknife.ButterKnife
import com.meoyawn.remotelove.HandleBack
import kotlin.properties.Delegates
import android.widget.ProgressBar

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoginFragment : LoginFragmentBase(), HandleBack {
  val username: EditText by Delegates.lazy { usernameStub.inflate() as EditText }
  val password: EditText by Delegates.lazy { passwordStub.inflate() as EditText }
  val progress: ProgressBar by Delegates.lazy { progressStub.inflate() as ProgressBar }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
      inflater.inflate(R.layout.login, container, false)

  override fun onCreate(savedInstanceState: Bundle?) {
    super<LoginFragmentBase>.onCreate(savedInstanceState)
    viewCreates
        .flatMap { view ->
          ButterKnife.inject(this, view)
          username.submits()
              .flatMap {
                if (TextUtils.isEmpty(it))
                  Observable.empty<String>()
                else {
                  password.setVisibility(View.INVISIBLE)

                  val set = AnimatorSet()
                  val width = view!!.getWidth().toFloat()
                  val exit = ObjectAnimator.ofFloat(username, View.X, -width)
                  exit.ends().subscribe {
                    password.setVisibility(View.VISIBLE)
                  }
                  set.playSequentially(
                      exit,
                      ObjectAnimator.ofFloat(password, View.X, width, username.getX()))
                  set.setDuration(200)
                  set.start()
                  password.submits()
                }
              }
        }!!
        .takeUntil(viewDestroys)!!
        .subscribe()
  }

  override fun handleBack(): Boolean = when {
    progress.getVisibility() == View.VISIBLE -> {
      true
    }
    password.getVisibility() == View.VISIBLE -> {

      true
    }
    else                                     -> false
  }
}
