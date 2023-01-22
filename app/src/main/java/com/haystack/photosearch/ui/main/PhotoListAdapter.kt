package com.haystack.photosearch.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haystack.photosearch.databinding.ListPhotoGalleryBinding
import com.haystack.photosearch.domain.Photo
import com.haystack.photosearch.ui.common.loadUrl

class PhotoViewHolder(private val binding: ListPhotoGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(galleryPhoto: Photo) {
        binding.photoImageView.loadUrl(galleryPhoto.url)
    }
}
private const val TAG = "PhotoListAdapter"

class PhotoListAdapter(
    private val galleryPhotos: List<Photo>
) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListPhotoGalleryBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = galleryPhotos[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            Log.d(TAG, "Item id: ${item.id}")
        }
    }

    override fun getItemCount() = galleryPhotos.size
}