package com.haystack.photosearch.ui.detail

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.haystack.photosearch.R
import com.haystack.photosearch.databinding.ActivityPhotoDetailBinding
import com.haystack.photosearch.ui.common.loadUrl

class PhotoDetailActivity : Activity() {
    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoTitle = intent.getStringExtra("TITLE").toString()
        val photoUrl = intent.getStringExtra("URL").toString()

        val imageView = findViewById<ImageView>(R.id.detailedPhoto)
        imageView.loadUrl(photoUrl)
        val textView = findViewById<TextView>(R.id.detailedPhotoTitle)
        textView.text = photoTitle
    }
}
