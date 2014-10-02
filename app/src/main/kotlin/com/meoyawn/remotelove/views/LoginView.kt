package com.meoyawn.remotelove.views

import android.content.Context
import android.util.AttributeSet
import com.meoyawn.remotelove.widgets.submits
import android.animation.AnimatorSet
import android.view.View
import android.animation.ObjectAnimator
import com.meoyawn.remotelove.widgets.ends

/**
 * Created by adelnizamutdinov on 10/1/14
 */
class LoginView(ctx: Context, attrs: AttributeSet? = null) : LoginViewBase(ctx, attrs) {
    override fun onFinishInflate() {
        super<LoginViewBase>.onFinishInflate()
        username.submits()
                .flatMap { name ->
                    val set: AnimatorSet = AnimatorSet()
                    val width = this.getWidth().toFloat()
                    val outAnim = ObjectAnimator.ofFloat(username, "x", -width)
                    outAnim.ends().subscribe {
                        password.setVisibility(View.VISIBLE)
                        password.requestFocus()
                    }
                    set.playSequentially(outAnim, ObjectAnimator.ofFloat(password, "x", width, username.getX()))
                    set.setDuration(200)
                    set.start()
                    password.submits()
                }!!
                .subscribe()
    }
}