package com.meoyawn.remotelove.fragment

import android.app.Fragment
import rx.subjects.PublishSubject

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
    viewDestroys.onNext(this)
    super<Fragment>.onDestroyView()
  }

  override fun onDestroy() {
    if (getActivity()?.isFinishing()!! || !isAdded()) {
      destroys.onNext(this)
    }
    super<Fragment>.onDestroy()
  }
}