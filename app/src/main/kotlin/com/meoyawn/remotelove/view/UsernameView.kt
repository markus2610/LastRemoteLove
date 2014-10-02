package com.meoyawn.remotelove.view

import android.content.Context
import android.util.AttributeSet
import butterknife.ButterKnife
import com.meoyawn.remotelove.widget.submits
import com.example.flow.appflow.AppFlow
import com.meoyawn.remotelove.screen.PasswordScreen

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class UsernameView(ctx: Context, attrs: AttributeSet) : UsernameViewBase(ctx, attrs) {
    override fun onFinishInflate() {
        super<UsernameViewBase>.onFinishInflate()
        ButterKnife.inject(this)
        username.submits()
                .takeUntil(detaches)!!
                .subscribe {
                    AppFlow.get(getContext()).goTo(PasswordScreen(it as String))
                }
    }
}