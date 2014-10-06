package com.meoyawn.remotelove

import android.os.Bundle
import com.meoyawn.remotelove.fragment.LoginFragment
import java.lang.ref.WeakReference
import android.app.Fragment
import java.util.ArrayList
import com.meoyawn.remotelove.fragment.LoveFragment
import com.crashlytics.android.Crashlytics

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoveActivity : LoveActivityBase() {
  val fragments: MutableList<WeakReference<Fragment>> = ArrayList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super<LoveActivityBase>.onCreate(savedInstanceState)
    Dagger.inject(this)
    Crashlytics.start(this)
    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .replace(android.R.id.content,
                   if (preferencesLazy.get()?.session() != null) LoveFragment()
                   else LoginFragment())
          .commit()
    }
  }
  override fun onAttachFragment(fragment: Fragment?) {
    super<LoveActivityBase>.onAttachFragment(fragment)
    fragments.add(WeakReference(fragment));
  }

  fun activeFragments(): List<Fragment> {
    val ret = ArrayList<Fragment>(fragments.size)
    for (ref in fragments) {
      val fragment = ref.get();
      if (fragment != null && !fragment.isHidden()) {
        ret.add(fragment)
      }
    }
    return ret
  }

  override fun onBackPressed() {
    for (fragment in activeFragments()) {
      if (fragment is HandleBack && fragment.handleBack()) {
        return
      }
    }
    super<LoveActivityBase>.onBackPressed()
  }
}