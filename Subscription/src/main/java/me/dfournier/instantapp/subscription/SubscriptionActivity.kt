package me.dfournier.instantapp.subscription

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SubscriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, Subscription1Fragment())
                commit()
            }
        }
    }

}
