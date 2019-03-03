package me.dfournier.instantapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.instantapps.InstantApps
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    val splitInstallManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }

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
            val moduleName = getString(R.string.title_stock)
            val intent =
                Intent(Intent.ACTION_VIEW).setClassName(this, "me.dfournier.instantapp.subscription.StockActivity")

            if (splitInstallManager.installedModules.contains(moduleName)) {
                startActivity(intent)
            } else {
                stock_loading.visibility = View.VISIBLE
                stock_error.visibility = View.GONE
                stock_button.isEnabled = false
                val request = SplitInstallRequest
                    .newBuilder()
                    .addModule(moduleName)
                    .build()
                splitInstallManager.startInstall(request)
                    .addOnSuccessListener {
                        stock_loading.visibility = View.GONE
                        stock_button.isEnabled = true
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        stock_loading.visibility = View.GONE
                        stock_error.visibility = View.VISIBLE
                        stock_button.isEnabled = true
                    }
            }
        }
    }
}
