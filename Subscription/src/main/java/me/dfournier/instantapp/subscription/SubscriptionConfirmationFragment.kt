package me.dfournier.instantapp.subscription

import android.os.Build
import android.os.Bundle
import android.security.ConfirmationAlreadyPresentingException
import android.security.ConfirmationCallback
import android.security.ConfirmationNotAvailableException
import android.security.ConfirmationPrompt
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_subscribe_confirmation.*
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.util.*


@RequiresApi(Build.VERSION_CODES.P)
class SubscriptionConfirmationFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateKey(KEY_ALIAS, UUID.randomUUID().toString().toByteArray())

        action.setOnClickListener {

            val promptText = "Do you accept the T&C ?"
            val nonce = UUID.randomUUID().toString()
            val extraData = "$promptText:$nonce".toByteArray()

            val confirmationPrompt = ConfirmationPrompt.Builder(context!!)
                .setPromptText(promptText)
                .setExtraData(extraData)
                .build()


            try {
                confirmationPrompt.presentPrompt(activity!!.mainExecutor, object : ConfirmationCallback() {
                    override fun onConfirmed(dataThatWasConfirmed: ByteArray) {
                        super.onConfirmed(dataThatWasConfirmed)
                        try {
                            val signature = initSignature(KEY_ALIAS) ?: return

                            signature.update(dataThatWasConfirmed)
                            val signatureBytes = signature.sign()

                            // dataThatWasConfirmed and signatureBytes should be sent to RP
                            // RP verifies the signature with Kpub and the message (dataThatWasConfirmed) is identical to the challenge
                            Log.i(
                                TAG,
                                "dataThatWasConfirmed: " + Base64.getEncoder().encode(dataThatWasConfirmed)
                            )
                            Log.i(TAG, "signature: " + Base64.getEncoder().encode(signatureBytes))
                        } catch (e: Exception) {
                            throw RuntimeException(e)
                        }
                        goToNextStep(this::class)
                    }
                })
            } catch (e: ConfirmationAlreadyPresentingException) {
                confirmationPrompt.cancelPrompt()
            } catch (e: ConfirmationNotAvailableException) {
                Log.w(TAG, "Confirmation Prompt is not supported on this device")
            }


        }
    }

    private fun generateKey(keyName: String, challenge: ByteArray): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore")

        val builder = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_SIGN
        )
            .setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
            .setDigests(
                KeyProperties.DIGEST_SHA256,
                KeyProperties.DIGEST_SHA384,
                KeyProperties.DIGEST_SHA512
            )
            .setUserConfirmationRequired(true)
            .setAttestationChallenge(challenge)

        keyPairGenerator.initialize(builder.build())

        return keyPairGenerator.generateKeyPair()
    }

    @Throws(Exception::class)
    private fun initSignature(keyName: String): Signature? {
        val keyPair = getKeyPair(keyName)

        if (keyPair != null) {
            val signature = Signature.getInstance("SHA256withECDSA")
            signature.initSign(keyPair.private)
            return signature
        }
        return null
    }

    @Throws(Exception::class)
    private fun getKeyPair(keyName: String): KeyPair? {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        if (keyStore.containsAlias(keyName)) {
            // Get public key
            val publicKey = keyStore.getCertificate(keyName).publicKey
            // Get private key
            val privateKey = keyStore.getKey(keyName, null) as PrivateKey
            // Return a key pair
            return KeyPair(publicKey, privateKey)
        }
        return null
    }

}

private const val KEY_ALIAS = "alias"
private val TAG = SubscriptionConfirmationFragment::class.java.simpleName
