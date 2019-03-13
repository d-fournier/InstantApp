package me.dfournier.instantapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.instantapps.InstantApps
import com.google.android.play.core.splitinstall.SplitInstallHelper
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlinx.android.synthetic.main.activity_main.*
import me.dfournier.instantapp.core.Activities
import me.dfournier.instantapp.core.intentTo


class MainActivity : BaseActivity() {

    private val splitInstallManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribe_button.setOnClickListener {
            startActivity(
                intentTo(Activities.Subscription)
            )
        }

        val isInstantApp = InstantApps.getPackageManagerCompat(this).isInstantApp

        account_button.isEnabled = !isInstantApp
        stock_button.isEnabled = !isInstantApp

        account_button.setOnClickListener {
            startActivity(
                intentTo(Activities.AccountManagement)
            )
        }

        stock_button.setOnClickListener {
            val moduleName = getString(R.string.title_stock)
            val intent = intentTo(Activities.Stock)

            if (moduleName in splitInstallManager.installedModules) {
                launchIntent(intent)
            } else {
                stock_loading.visibility = View.VISIBLE
                stock_error.visibility = View.GONE
                stock_button.isEnabled = false
                val request = SplitInstallRequest
                    .newBuilder()
                    .addModule(moduleName)
                    .build()

                splitInstallManager.registerListener { splitInstallSessionState ->
                    when (splitInstallSessionState.status()) {
                        SplitInstallSessionStatus.INSTALLED -> {
                            SplitInstallHelper.updateAppInfo(this@MainActivity)
                            stock_loading.visibility = View.GONE
                            stock_button.isEnabled = true
                            launchIntent(intent)
                        }
                        SplitInstallSessionStatus.FAILED -> {
                            Log.e("MainActivity", "SplitInstallManager error ${splitInstallSessionState.errorCode()}")
                            stock_loading.visibility = View.GONE
                            stock_error.visibility = View.VISIBLE
                            stock_button.isEnabled = true
                        }
                        else -> {
                            Log.e("MainActivity", "SplitInstallManager status ${splitInstallSessionState.status()}")
                        }
                    }
                }
                splitInstallManager.startInstall(request)
            }
        }
    }

    private fun launchIntent(intent: Intent) {
        startActivity(intent)
    }
}
