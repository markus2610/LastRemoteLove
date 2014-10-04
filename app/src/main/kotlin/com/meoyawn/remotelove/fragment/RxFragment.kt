package com.meoyawn.remotelove.fragment

import android.app.Fragment
import rx.subjects.PublishSubject

/**
 * Created by adelnizamutdinov on 10/3/14
 */
open class RxFragment : Fragment() {
  val viewDestroys = PublishSubject.create<Fragment>() as PublishSubject<Fragment>

  override fun onDestroyView() {
    viewDestroys.onNext(this)
    super<Fragment>.onDestroyView()
  }
}