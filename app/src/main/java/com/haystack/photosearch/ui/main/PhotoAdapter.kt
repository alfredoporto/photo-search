package com.haystack.photosearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haystack.photosearch.databinding.ViewPhotoItemBinding
import com.haystack.photosearch.domain.Image

class PhotoAdapter(var photos: List<Image>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(val binding: ViewPhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Image) {
            binding.photoTitle.text = photo.title
            binding.photoDescription.text = "Alfredo"
            Glide
                .with(binding.root.context)
                .load(photo.url)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ViewPhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
        // if I want to go into details
        // holder.itemView.setOnClickListener { }
    }

    override fun getItemCount() = photos.size


}