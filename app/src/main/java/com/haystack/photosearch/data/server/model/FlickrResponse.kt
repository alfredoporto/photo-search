package com.haystack.photosearch.data.server.model


import com.google.gson.annotations.SerializedName

data class FlickrResponse(
    @SerializedName("photos")
    val data: PhotosMetaData
)