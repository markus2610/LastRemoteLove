package com.meoyawn.remotelove.util

import com.example.flow.appflow.AppFlow
import android.content.ContextWrapper
import android.content.Context
import android.view.LayoutInflater
import kotlin.properties.Delegates

/**
 * Created by adelnizamutdinov on 10/3/14
 */
class AppFlowContextWrapper(val ctx: Context, val appFlow: AppFlow) : ContextWrapper(ctx) {
    val inflater by Delegates.lazy { LayoutInflater.from(ctx).cloneInContext(this) }

    override fun getSystemService(name: String): Any? = when {
        AppFlow.isAppFlowSystemService(name) -> appFlow
        Context.LAYOUT_INFLATER_SERVICE.equals(name) -> inflater
        else -> super<ContextWrapper>.getSystemService(name)
    }
}