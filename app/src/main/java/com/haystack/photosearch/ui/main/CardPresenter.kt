package com.haystack.photosearch.ui.main

import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.haystack.photosearch.domain.Photo
import com.haystack.photosearch.ui.common.loadUrl

class CardPresenter : Presenter() {
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
        val photo = item as Photo

        Log.d("CardPresenter", "Load image : $photo")

        with(viewHolder.view as ImageCardView) {
            titleText = photo.title
            contentText = photo.id
            mainImageView.loadUrl(photo.url)
            mainImageView.setOnClickListener {
                Log.d("NEW-FEATURE", "photo id: $photo.id")
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        with(viewHolder.view as ImageCardView) {
            // for the garbage collector
            mainImage = null
        }
    }
}