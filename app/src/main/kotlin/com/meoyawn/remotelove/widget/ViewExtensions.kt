package com.meoyawn.remotelove.widget

import rx.Observable
import rx.subjects.PublishSubject
import android.view.View

/**
 * Created by adelnizamutdinov on 10/4/14
 */
fun View.clicks(): Observable<View> {
  val s = PublishSubject.create<View>() as PublishSubject<View>
  this.setOnClickListener {
    s.onNext(it)
    true
  }
  return s
}
object Views {
  fun setVisibility(visibility: Int, views: Array<View>) {
    for (v in views) {
      v.setVisibility(visibility)
    }
  }
  fun setVisible(vararg views: View): Unit = setVisibility(View.VISIBLE, views)
  fun setInvisible(vararg views: View): Unit = setVisibility(View.INVISIBLE, views)
}