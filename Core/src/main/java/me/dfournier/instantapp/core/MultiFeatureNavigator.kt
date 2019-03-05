package me.dfournier.instantapp.core

import android.content.Intent


private const val PACKAGE_NAME = "me.dfournier.instantapp"

/**
 * An [android.app.Activity] that can be addressed by an intent.
 */
interface AddressableActivity {
    /**
     * The activity class name.
     */
    val className: String
}

/**
 * Create an Intent with [Intent.ACTION_VIEW] to an [AddressableActivity].
 */
fun intentTo(addressableActivity: AddressableActivity): Intent {
    return Intent(Intent.ACTION_VIEW).setClassName(
        PACKAGE_NAME,
        addressableActivity.className
    )
}

/**
 * All addressable activities.
 *
 * Can contain intent extra names or functions associated with the activity creation.
 */
object Activities {

    object Stock : AddressableActivity {
        override val className = "$PACKAGE_NAME.stock.StockActivity"
    }

    object Subscription : AddressableActivity {
        override val className = "$PACKAGE_NAME.subscription.SubscriptionActivity"
    }

    object AccountManagement : AddressableActivity {
        override val className = "$PACKAGE_NAME.account_management.AccountListActivity"
    }

}
