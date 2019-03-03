package me.dfournier.instantapp.subscription

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.instantapps.InstantApps
import kotlinx.android.synthetic.main.fragment_subscribe_confirmation.*

class SubscriptionConfirmationFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (InstantApps.getPackageManagerCompat(context!!).isInstantApp) {
            text_incentive_2.text = getString(R.string.subscription_incentive_download)
            action.text = getString(R.string.subscription_incentive_download_action)
            action.setOnClickListener {
                InstantApps.showInstallPrompt(activity!!, Intent(Intent.ACTION_VIEW), 0, "")
            }
        } else {
            text_incentive_2.text = getString(R.string.subscription_incentive_connect)
            action.text = getString(R.string.subscription_incentive_connect_action)
            action.setOnClickListener {
                activity?.finish()
            }
        }
    }
}