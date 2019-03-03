package me.dfournier.instantapp.subscription

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Animatable
import android.graphics.drawable.Animatable2
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.graphics.drawable.Animatable2Compat
import android.support.v4.widget.ImageViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_subscribe_document.*

class SubscriptionDocumentFragment : BaseFragment() {

    private val documents: Array<ImageView> by lazy {
        arrayOf(document_1, document_2, document_3, document_4)
    }

    private val processedDocument = mutableSetOf<Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe_document, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next.setOnClickListener {
            goToNextStep(this::class)
        }

        documents.forEachIndexed(::addListener)
    }

    private fun addListener(index: Int, iv: ImageView) {
        iv.tag = index
        iv.setOnClickListener {
            startPhoto(index)
        }
    }

    private fun startPhoto(index: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {
            startActivityForResult(takePictureIntent, index)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val doc = documents[requestCode]

            val bitmapData = data?.extras?.get("data") as Bitmap? ?: return

            val drawable = doc.drawable
            if (drawable is Animatable2Compat) {
                drawable.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable?) {
                        loadImage(doc, bitmapData)
                    }
                })
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && drawable is Animatable2) {
                drawable.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable?) {
                        loadImage(doc, bitmapData)
                    }
                })
            }
            (drawable as Animatable).start()

            doc.setOnClickListener(null)
            processedDocument.add(doc.id)
            if (processedDocument.size >= documents.size) {
                next.isEnabled = true
            }
        }
    }

    private fun loadImage(iv: ImageView, bitmap: Bitmap) {
        iv.setImageBitmap(bitmap)
        iv.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageViewCompat.setImageTintList(iv, null)
    }
}
