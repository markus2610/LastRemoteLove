package com.meoyawn.remotelove

import android.app.Application
import dagger.ObjectGraph
import timber.log.Timber.DebugTree
import timber.log.Timber
import kotlin.properties.Delegates

/**
 * Created by adelnizamutdinov on 10/2/14
 */
open class App : Application() {
    val objectGraph by Delegates.lazy { ObjectGraph.create(LoveModule()) }

    override fun onCreate() {
        super<Application >.onCreate()
        Timber.plant(DebugTree())
    }

    protected open fun getModules(): List<Any> = listOf(LoveModule())
}