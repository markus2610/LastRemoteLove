package com.meoyawn.remotelove.view

import android.content.Context
import android.util.AttributeSet
import butterknife.ButterKnife
import com.meoyawn.remotelove.widget.submits
import com.example.flow.appflow.AppFlow
import com.meoyawn.remotelove.screen.PasswordScreen
import com.meoyawn.remotelove.screen.ProgressScreen

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class PasswordView(ctx: Context, attrs: AttributeSet) : PasswordViewBase(ctx, attrs) {
    override fun onFinishInflate() {
        super<PasswordViewBase>.onFinishInflate()
        ButterKnife.inject(this)
        password.submits()
                .takeUntil(detaches)!!
                .subscribe {
                    val screen = AppFlow.getScreen<PasswordScreen>(getContext())
                    AppFlow.get(getContext()).goTo(ProgressScreen(screen.username, it as String))
                }
    }
}