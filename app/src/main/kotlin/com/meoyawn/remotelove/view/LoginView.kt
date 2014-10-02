package com.meoyawn.remotelove.view

import android.content.Context
import android.util.AttributeSet
import kotlin.properties.Delegates
import com.example.flow.appflow.FlowBundler
import com.meoyawn.remotelove.screen.UsernameScreen
import flow.Flow
import flow.Backstack
import com.example.flow.appflow.AppFlow
import com.example.flow.appflow.Screen
import com.meoyawn.remotelove.Dagger
import com.meoyawn.remotelove.util.AppFlowContextWrapper
import com.example.flow.screenswitcher.FrameScreenSwitcherView
import android.view.ViewGroup
import android.widget.LinearLayout.LayoutParams
import com.example.flow.screenswitcher.HandlesBack

/**
 * Created by adelnizamutdinov on 10/1/14
 */
class LoginView(ctx: Context, attrs: AttributeSet? = null) : LoginViewBase(ctx, attrs), Flow.Listener, HandlesBack {
    val flowBundler by Delegates.lazy { FlowBundler(UsernameScreen(), this, parcer) }
    val appFlow: AppFlow by Delegates.lazy { flowBundler.onCreate(null) }
    val c: Context by Delegates.lazy { AppFlowContextWrapper(getContext() as Context, appFlow) }
    val container: FrameScreenSwitcherView by Delegates.lazy { FrameScreenSwitcherView(c, null) }

    override fun onFinishInflate() {
        super<LoginViewBase>.onFinishInflate()
        Dagger.inject(this)

        addView(container, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f))

        AppFlow.loadInitialScreen(c)
    }

    override fun go(nextBackstack: Backstack?, direction: Flow.Direction?, callback: Flow.Callback?) {
        val screen = nextBackstack?.current()?.getScreen() as Screen
        container.showScreen(screen, direction, callback)
    }

    override fun onBackPressed(): Boolean {
        return AppFlow.get(c).goBack();
    }
}
