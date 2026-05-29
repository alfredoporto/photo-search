package com.haystack.photosearch.data.repository

import com.haystack.photosearch.domain.Photo

interface PhotosRepository {
    suspend fun searchPhotos(query: String) : List<Photo>
    suspend fun fetchPhotos() : List<Photo>
}