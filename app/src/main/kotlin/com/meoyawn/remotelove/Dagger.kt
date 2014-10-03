package com.meoyawn.remotelove

import android.content.Context
import android.view.View
import android.app.Activity
import android.app.Fragment

/**
 * Created by adelnizamutdinov on 10/3/14
 */
object Dagger {
  fun inject(c: Context?, what: Any) {
    (c?.getApplicationContext() as App).objectGraph?.inject(what)
  }
  fun inject(v: View) {
    inject(v.getContext(), v)
  }
  fun inject(a: Activity) {
    inject(a, a)
  }
  fun inject(f: Fragment) {
    inject(f.getActivity(), f)
  }
}