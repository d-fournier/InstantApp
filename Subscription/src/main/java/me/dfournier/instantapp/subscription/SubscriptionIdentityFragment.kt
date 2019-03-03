package me.dfournier.instantapp.subscription

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_subscribe_identity.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView



class SubscriptionIdentityFragment : BaseFragment() {

    private val inputText: Array<out EditText> by lazy {
        arrayOf(name_value, surname_value)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe_identity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputText.forEach {
            it.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val allFilled = inputText.fold(true) { acc, editText ->
                        acc && editText.text.isNotBlank()
                    }
                    next.isEnabled = allFilled
                }
            })
        }

        surname_value.setOnEditorActionListener { editText, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                goToNextStep(this::class)
                true
            } else {
                false
            }
        }

        next.setOnClickListener {
            goToNextStep(this::class)
        }
    }
}