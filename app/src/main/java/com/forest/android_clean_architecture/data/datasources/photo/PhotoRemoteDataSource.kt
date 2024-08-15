package com.forest.android_clean_architecture.data.datasources.photo

import retrofit2.http.GET
import com.forest.android_clean_architecture.data.config.AppConfig
import com.forest.android_clean_architecture.data.models.PhotosResponse
interface PhotoRemoteDataSource {
    @GET("?key=${AppConfig.API_KEY}")
    suspend fun getPhoto(): PhotosResponse
}