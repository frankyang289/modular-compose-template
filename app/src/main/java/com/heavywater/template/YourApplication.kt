package com.heavywater.template

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YourApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setStrictMode()
    }

    private fun isDebuggable(): Boolean {
        return 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
    }

    private fun setStrictMode() {
        if (isDebuggable()) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build()
            )
        }
    }
}