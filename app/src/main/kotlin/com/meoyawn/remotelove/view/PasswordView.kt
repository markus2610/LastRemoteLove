package com.meoyawn.remotelove.view

import android.content.Context
import android.util.AttributeSet
import butterknife.ButterKnife

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class PasswordView(ctx: Context, attrs: AttributeSet) : PasswordViewBase(ctx, attrs) {
    override fun onFinishInflate() {
        super<PasswordViewBase>.onFinishInflate()
        ButterKnife.inject(this)
    }
}