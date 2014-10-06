package com.meoyawn.remotelove.fragment

import android.app.Fragment
import rx.subjects.PublishSubject
import timber.log.Timber

/**
 * Created by adelnizamutdinov on 10/3/14
 */
open class RxFragment : Fragment() {
  val pauses = PublishSubject.create<Fragment>()!!
  val viewDestroys = PublishSubject.create<Fragment>()!!
  val destroys = PublishSubject.create<Fragment>()!!

  override fun onPause() {
    pauses.onNext(this)
    super<Fragment>.onPause()
  }

  override fun onDestroyView() {
    Timber.d("destroyview triggering")
    viewDestroys.onNext(this)
    super<Fragment>.onDestroyView()
  }

  override fun onDestroy() {
    if (getActivity()?.isFinishing()!! || !isAdded()) {
      Timber.d("destroy triggering")
      destroys.onNext(this)
    }
    super<Fragment>.onDestroy()
  }
}