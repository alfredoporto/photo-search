package com.haystack.photosearch.data.server

import com.haystack.photosearch.data.server.model.FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    //TODO: refactor interface to avoid exposure of api key

    // safe_search to 1 filters potentially offensive results
    @GET("?method=flickr.photos.search")
    suspend fun searchPhotos(@Query(value = "text") query: String): FlickrResponse

    @GET("?method=flickr.interestingness.getList")
    suspend fun fetchPhotos(): FlickrResponse
}