package com.meoyawn.remotelove

import android.os.Bundle
import android.app.Activity
import com.meoyawn.remotelove.fragment.LoginFragment

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoveActivity : Activity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super<Activity>.onCreate(savedInstanceState)
    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .add(android.R.id.content, LoginFragment())
          .commit()
    }
  }
}