package me.dfournier.instantapp.subscription

import android.os.Build
import android.security.ConfirmationPrompt
import android.support.v4.app.Fragment
import kotlin.reflect.KClass

open class BaseFragment : Fragment() {

    fun goToNextStep(clazz: KClass<*>) {
        val fragment = when (clazz) {
            SubscriptionWelcomeFragment::class -> SubscriptionIdentityFragment()
            SubscriptionIdentityFragment::class -> SubscriptionMapFragment()
            SubscriptionMapFragment::class -> SubscriptionPlanFragment()
            SubscriptionPlanFragment::class -> SubscriptionDocumentFragment()
            SubscriptionDocumentFragment::class -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && ConfirmationPrompt.isSupported(context!!)) {
                    SubscriptionConfirmationFragment()
                } else {
                    SubscriptionLoadingFragment()
                }
            }
            SubscriptionConfirmationFragment::class -> SubscriptionLoadingFragment()
            SubscriptionLoadingFragment::class -> SubscriptionEndFragment()
            else -> null
        }
        if (fragment != null) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                ?.replace(R.id.container, fragment)
                ?.commit()
        }
    }
}
