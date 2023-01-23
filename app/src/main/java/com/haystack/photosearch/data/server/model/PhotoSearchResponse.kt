package com.haystack.photosearch.data.server.model


import com.google.gson.annotations.SerializedName

data class PhotoSearchResponse(
    @SerializedName("photos")
    val data: PhotosMetaData
)