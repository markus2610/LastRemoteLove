package com.meoyawn.remotelove.fragment

import android.app.Fragment
import android.view.View
import android.os.Bundle
import rx.subjects.PublishSubject

/**
 * Created by adelnizamutdinov on 10/3/14
 */
open class RxFragment : Fragment() {
    val viewCreates = PublishSubject.create<View>() as PublishSubject<View>
    val viewDestroys = PublishSubject.create<Fragment>() as PublishSubject<Fragment>
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super<Fragment>.onViewCreated(view as View, savedInstanceState)
        viewCreates.onNext(view)
    }
    override fun onDestroyView() {
        viewDestroys.onNext(this)
        super<Fragment>.onDestroyView()
    }
}