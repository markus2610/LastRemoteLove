package com.meoyawn.remotelove.view

import android.content.Context
import android.util.AttributeSet
import kotlin.properties.Delegates
import com.example.flow.appflow.FlowBundler
import com.meoyawn.remotelove.screen.UsernameScreen
import flow.Flow
import flow.Backstack
import com.example.flow.appflow.AppFlow
import android.os.Parcelable
import android.os.Bundle
import com.example.flow.appflow.Screen
import com.meoyawn.remotelove.Dagger
import butterknife.ButterKnife
import com.meoyawn.remotelove.util.AppFlowContextWrapper

/**
 * Created by adelnizamutdinov on 10/1/14
 */
class LoginView(ctx: Context, attrs: AttributeSet? = null) : LoginViewBase(ctx, attrs), Flow.Listener {
    val flowBundler by Delegates.lazy { FlowBundler(UsernameScreen(), this, parcer) }
    var appFlow: AppFlow by Delegates.notNull()

    override fun onFinishInflate() {
        super<LoginViewBase>.onFinishInflate()
        Dagger.inject(this)
        ButterKnife.inject(this)

        appFlow = flowBundler.onCreate(null)
        AppFlow.loadInitialScreen(AppFlowContextWrapper(getContext() as Context, appFlow))
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var mut = state
        if (state is Bundle) {
            flowBundler.onCreate(state)
            mut = state.getParcelable("instanceState")
        }
        super<LoginViewBase>.onRestoreInstanceState(mut)
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("instanceState", super<LoginViewBase>.onSaveInstanceState())
        flowBundler.onSaveInstanceState(bundle)
        return bundle
    }

    override fun go(nextBackstack: Backstack?, direction: Flow.Direction?, callback: Flow.Callback?) {
        val screen = nextBackstack?.current()?.getScreen() as Screen
        container.showScreen(screen, direction, callback)
    }
}