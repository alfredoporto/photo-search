package com.haystack.photosearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haystack.photosearch.databinding.ViewPhotoItemBinding
import com.haystack.photosearch.domain.Image

class MyAdapter() : RecyclerView.Adapter<MyAdapter.PhotoViewHolder>() {

    private var oldData = emptyList<Image>()

    class PhotoViewHolder(val binding: ViewPhotoItemBinding) : RecyclerView.ViewHolder(binding.root)

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
        holder.binding.photoTitle.text = oldData[position].title
        holder.binding.photoDescription.text = oldData[position].id

        Glide
            .with(holder.binding.root.context)
            .load(oldData[position].url)
            .into(holder.binding.image)

        // if I want to go into details
        // holder.itemView.setOnClickListener { }
    }

    override fun getItemCount(): Int {
        return oldData.size
    }

    fun setData(newData: List<Image>){
        oldData = newData
        notifyDataSetChanged()
    }

}