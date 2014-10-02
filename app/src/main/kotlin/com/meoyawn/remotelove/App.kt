package com.meoyawn.remotelove

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by adelnizamutdinov on 10/2/14
 */
class App : Application() {
    override fun onCreate() {
        super<Application>.onCreate()
        Timber.plant(DebugTree())
    }
}