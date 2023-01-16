package com.haystack.photosearch.data.repository

import androidx.leanback.widget.ArrayObjectAdapter
import com.haystack.photosearch.R
import com.haystack.photosearch.data.server.WebClient
import com.haystack.photosearch.data.server.model.toDomain
import com.haystack.photosearch.domain.Image
import java.util.concurrent.Flow

class PhotoRepository {
    suspend fun getPhotos() : List<Image> {
        return emptyList()
    }
}