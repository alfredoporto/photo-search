package com.haystack.photosearch.data.server

import com.haystack.photosearch.data.server.model.PhotoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=a85e8cfe117f56b39765a1a4ab69af42")
    suspend fun fetchImages(@Query(value = "text") text: String): PhotoSearchResponse
}