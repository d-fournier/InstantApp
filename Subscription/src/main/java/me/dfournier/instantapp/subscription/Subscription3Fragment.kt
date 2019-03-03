package me.dfournier.instantapp.subscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_subscribe_3.*

class Subscription3Fragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plan_A.setOnClickListener { radio_group.check(it.id) }
        plan_B.setOnClickListener { radio_group.check(it.id) }
        plan_C.setOnClickListener { radio_group.check(it.id) }

        next.setOnClickListener {
            goToNextStep(this::class)
        }
    }
}