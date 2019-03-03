package me.dfournier.instantapp.subscription

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_subscribe_4.*

class Subscription4Fragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe_4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next.setOnClickListener {
            goToNextStep(this::class)
        }

        addListener(document_1)
        addListener(document_2)
        addListener(document_3)
        addListener(document_4)
    }

    private fun addListener(iv: ImageView) {
        iv.setOnClickListener {
            val drawable = iv.drawable
            (drawable as Animatable).start()
            iv.setOnClickListener(null)
        }
    }
}