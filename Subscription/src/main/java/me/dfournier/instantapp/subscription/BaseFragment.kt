package me.dfournier.instantapp.subscription

import android.support.v4.app.Fragment
import kotlin.reflect.KClass

open class BaseFragment : Fragment() {

    fun goToNextStep(clazz: KClass<*>) {
        val fragment = when (clazz) {
            Subscription1Fragment::class -> Subscription2Fragment()
            Subscription2Fragment::class -> Subscription3Fragment()
            Subscription3Fragment::class -> Subscription4Fragment()
            Subscription4Fragment::class -> Subscription5Fragment()
            Subscription5Fragment::class -> Subscription6Fragment()
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
