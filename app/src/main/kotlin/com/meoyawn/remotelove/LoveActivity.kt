package com.meoyawn.remotelove

import com.example.flow.appflow.FlowBundler
import kotlin.properties.Delegates
import com.meoyawn.remotelove.screen.LoginScreen
import flow.Flow
import flow.Backstack
import android.os.Bundle
import com.example.flow.appflow.AppFlow
import butterknife.ButterKnife
import android.view.MenuItem
import com.example.flow.appflow.Screen
import flow.HasParent

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class LoveActivity : LoveActivityBase(), Flow.Listener {
    val flowBundler by Delegates.lazy { FlowBundler(LoginScreen(), this, parcer) }
    var appFlow: AppFlow by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super<LoveActivityBase>.onCreate(savedInstanceState)
        Dagger.inject(this, this)
        appFlow = flowBundler.onCreate(savedInstanceState) as AppFlow

        setContentView(R.layout.activity_main)
        ButterKnife.inject(this)
        AppFlow.loadInitialScreen(this)
    }

    override fun getSystemService(name: String): Any = when {
        AppFlow.isAppFlowSystemService(name) -> appFlow
        else -> super<LoveActivityBase>.getSystemService(name) as Any
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super<LoveActivityBase>.onSaveInstanceState(outState)
        flowBundler.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when {
        item?.getItemId() == android.R.id.home -> container.onUpPressed()
        else -> super<LoveActivityBase>.onOptionsItemSelected(item)
    }

    override fun onBackPressed(): Unit = when {
        !container.onBackPressed() -> super<LoveActivityBase>.onBackPressed()
    }

    override fun go(nextBackstack: Backstack?, direction: Flow.Direction?, callback: Flow.Callback?) {
        val screen = nextBackstack?.current()?.getScreen() as Screen
        container.showScreen(screen, direction, callback)

        setTitle(screen.getClass()?.getSimpleName())

        val actionBar = getActionBar()
        val hasUp = screen is HasParent<*>
        actionBar?.setDisplayHomeAsUpEnabled(hasUp)
        actionBar?.setHomeButtonEnabled(hasUp)

        invalidateOptionsMenu()
    }
}