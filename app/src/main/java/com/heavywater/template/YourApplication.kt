package com.heavywater.template

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp
class YourApplication : Application() {

    @Inject
    lateinit var okHttpClient: dagger.Lazy<OkHttpClient>

    override fun onCreate() {
        super.onCreate()

        setStrictMode()

        // Warm up OkHttpClient on a background thread to avoid StrictMode violation
        // when it's first used on the main thread (e.g. by a ViewModel).
        MainScope().launch(Dispatchers.IO) {
            okHttpClient.get()
        }
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