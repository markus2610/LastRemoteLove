package com.meoyawn.remotelove.fragment

import com.meoyawn.remotelove.LoginFragmentBase
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.meoyawn.remotelove.R
import butterknife.ButterKnife
import com.meoyawn.remotelove.Dagger

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoginFragment : LoginFragmentBase() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
      inflater.inflate(R.layout.login, container, false)

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super<LoginFragmentBase>.onViewCreated(view, savedInstanceState)
    Dagger.inject(this)
    ButterKnife.inject(this, view)
  }
}