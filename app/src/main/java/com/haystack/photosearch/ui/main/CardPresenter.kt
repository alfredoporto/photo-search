package com.haystack.photosearch.ui.main

import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.haystack.photosearch.R
import com.haystack.photosearch.domain.Image
import com.haystack.photosearch.ui.common.loadUrl

class CardPresenter: Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val cardView = ImageCardView(parent.context).apply {
            isFocusable = true
            //isFocusableInTouchMode = true
            setMainImageScaleType(ImageView.ScaleType.CENTER_CROP)
            setMainImageDimensions(450, 300)
        }

        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val image = item as Image

        Log.d("CardPresenter", "Load image : $image")

        with(viewHolder.view as ImageCardView) {
            titleText = image.title
            contentText = image.id
            mainImageView.loadUrl(image.url)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        with(viewHolder.view as ImageCardView) {
            // for the garbage collector
            mainImage = null
        }
    }

}