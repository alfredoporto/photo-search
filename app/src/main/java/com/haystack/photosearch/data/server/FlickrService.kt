package com.haystack.photosearch.data.server

import com.haystack.photosearch.data.server.model.PhotoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrService {
    //TODO: refactor interface to avoid exposure of api key
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=9e99ddcabbfe506347053f8515b69886")
    suspend fun fetchImages(@Query(value = "text") text: String): PhotoSearchResponse
}