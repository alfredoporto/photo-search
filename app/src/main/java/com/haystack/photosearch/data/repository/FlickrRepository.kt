package com.haystack.photosearch.data.repository

import com.haystack.photosearch.data.server.WebClient
import com.haystack.photosearch.data.server.model.toDomain
import com.haystack.photosearch.domain.Photo

class FlickrRepository : PhotosRepository {
    private val flickrApi by lazy { WebClient.service }
    override suspend fun searchPhotos(query: String): List<Photo> {
        return flickrApi.searchPhotos(query).data.photos.map { it.toDomain() }
    }

    override suspend fun fetchPhotos(): List<Photo> {
        return flickrApi.fetchPhotos().data.photos.map { it.toDomain() }
    }

}