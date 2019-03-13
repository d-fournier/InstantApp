package me.dfournier.instantapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(ctx: Context?) {
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }

}