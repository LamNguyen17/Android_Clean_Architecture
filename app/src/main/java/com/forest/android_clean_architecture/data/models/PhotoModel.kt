package com.forest.android_clean_architecture.data.models

import com.forest.android_clean_architecture.domain.entities.photo.Hits

data class PhotosResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<HitsResponse>,
)

data class HitsResponse(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    val largeImageURL: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
    val user: String,
    val userImageURL: String,
)

fun HitsResponse.toEntity(): Hits {
    return Hits(
        id = id,
        pageURL = pageURL,
        type = type,
        tags = tags,
        previewURL = previewURL,
        previewWidth = previewWidth,
        previewHeight = previewHeight,
        webformatURL = webformatURL,
        webformatWidth = webformatWidth,
        webformatHeight = webformatHeight,
        largeImageURL = largeImageURL,
        imageWidth = imageWidth,
        imageHeight = imageHeight,
        imageSize = imageSize,
        views = views,
        downloads = downloads,
        collections = collections,
        likes = likes,
        comments = comments,
        user = user,
        userImageURL = userImageURL
    )
}