package com.haystack.photosearch.data.server.model


import com.google.gson.annotations.SerializedName
import com.haystack.photosearch.domain.Image

data class PhotoResponse(
    @SerializedName("farm")
    val farm: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("title")
    val title: String
)

fun PhotoResponse.toDomain(): Image {
    val url = "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}.jpg"

    return Image(
        id,
        title,
        url
    )
}

