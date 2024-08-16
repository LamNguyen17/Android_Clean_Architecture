package com.forest.android_clean_architecture.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient

import com.forest.android_clean_architecture.data.config.AppConfig
import com.forest.android_clean_architecture.data.config.AuthInterceptor
import com.forest.android_clean_architecture.data.config.LoggingInterceptor
import com.forest.android_clean_architecture.data.datasources.photo.PhotoRemoteDataSource
import com.forest.android_clean_architecture.data.repositories.PhotoRepositoryImpl
import com.forest.android_clean_architecture.domain.repositories.PhotoRepository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: LoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PhotoRemoteDataSource {
        return retrofit.create(PhotoRemoteDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideApiRepository(apiService: PhotoRemoteDataSource): PhotoRepository {
        return PhotoRepositoryImpl(apiService)
    }
}