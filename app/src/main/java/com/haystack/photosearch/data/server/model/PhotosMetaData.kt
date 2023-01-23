package com.haystack.photosearch.data.server.model


import com.google.gson.annotations.SerializedName

data class PhotosMetaData(
    @SerializedName("page")
    val page: Int,
    @SerializedName("photo")
    val photos: List<PhotoResponse>,
)
