package com.meoyawn.remotelove.widget

import android.view.View
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.meoyawn.remotelove.util.ends
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator

/**
 * Created by adelnizamutdinov on 10/3/14
 */
fun translate(container: View?): (View, View, Boolean) -> Unit = {(from, to, dir) ->
  if (container != null) {
    to.setVisibility(View.INVISIBLE);
    val set = AnimatorSet()
    val width = container.getWidth().toFloat()
    val exit = ObjectAnimator.ofFloat(from,
                                      View.TRANSLATION_X,
                                      0f,
                                      if (dir) -width else width)
    exit.ends().subscribe {
      from.setVisibility(View.INVISIBLE)
      to.setVisibility(View.VISIBLE)
    }
    set.playSequentially(exit, ObjectAnimator.ofFloat(to,
                                                      View.TRANSLATION_X,
                                                      if (dir) width else -width,
                                                      0f))
    set.setDuration(200)
    set.setInterpolator(if (!dir) AccelerateInterpolator() else DecelerateInterpolator())
    set.start()
  }
}