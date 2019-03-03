package me.dfournier.instantapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.instantapps.InstantApps
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribe_button.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW)
                    .setClassName(this, "me.dfournier.instantapp.subscription.SubscriptionActivity")
            )
        }

        val isInstantApp = InstantApps.getPackageManagerCompat(this).isInstantApp

        account_button.isEnabled = !isInstantApp
        stock_button.isEnabled = !isInstantApp

        account_button.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW)
                    .setClassName(this, "me.dfournier.instantapp.account_management.AccountListActivity")
            )
        }

        stock_button.setOnClickListener {
//            startActivity(
//                Intent(Intent.ACTION_VIEW)
//                    .setClassName(this, "me.dfournier.instantapp.subscription.StockActivity")
//            )
        }
    }
}
