package com.meoyawn.remotelove

import android.os.Bundle
import android.app.Activity
import com.meoyawn.remotelove.fragment.LoginFragment
import java.lang.ref.WeakReference
import android.app.Fragment
import java.util.ArrayList

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoveActivity : Activity() {
  val fragments: MutableList<WeakReference<Fragment>> = ArrayList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super<Activity>.onCreate(savedInstanceState)
    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .add(android.R.id.content, LoginFragment())
          .commit()
    }
  }
  override fun onAttachFragment(fragment: Fragment?) {
    super<Activity>.onAttachFragment(fragment)
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
    super<Activity>.onBackPressed()
  }
}