package com.haystack.photosearch.data.repository

import com.haystack.photosearch.data.server.WebClient
import com.haystack.photosearch.data.server.model.toDomain
import com.haystack.photosearch.domain.Photo

class FlickrRepository : PhotosRepository {
    private val flickrApi by lazy { WebClient.service }
    override suspend fun fetchPhotos(query: String): List<Photo> {
        return flickrApi.fetchImages(query).data.photos.map { it.toDomain() }
    }
}