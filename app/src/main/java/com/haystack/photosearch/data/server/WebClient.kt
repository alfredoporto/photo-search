package com.haystack.photosearch.data.server

import com.haystack.photosearch.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


object WebClient {
    private const val BASE_URL = "https://api.flickr.com/services/rest/"
    private const val CONNECTION_TIMEOUT_MS: Long = 10

    private val flickrInterceptor = Interceptor { chain ->
        val originalRequest: Request = chain.request()
        val apiKey = BuildConfig.FLICKR_API_KEY
        check(apiKey.isNotBlank()) {
            "Missing Flickr API key. Set flickrApiKey in local.properties or FLICKR_API_KEY in the environment."
        }

        val newUrl: HttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .addQueryParameter("extras", "url_s")
            .addQueryParameter("safesearch", "1")
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }


    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.SECONDS)
        .addInterceptor(flickrInterceptor)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                )
            }
        }
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: FlickrService = builder.create()
}
