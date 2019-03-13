package me.dfournier.instantapp

import android.app.Application
import android.content.Context
import com.google.android.gms.common.wrappers.InstantApps
import com.google.android.play.core.splitcompat.SplitCompat

class InstantApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
//        if (!InstantApps.isInstantApp(this)) {
            SplitCompat.install(this)
//        }
    }
}