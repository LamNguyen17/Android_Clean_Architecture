package com.forest.android_clean_architecture.domain.entities.photo

data class Photos(
    var total: Int,
    var totalHits: Int,
    var hits: List<Hits>,
)

data class Hits(
    var id: Int,
    var pageURL: String,
    var type: String,
    var tags: String,
    var previewURL: String,
    var previewWidth: Int,
    var previewHeight: Int,
    var webformatURL: String,
    var webformatWidth: Int,
    var webformatHeight: Int,
    var largeImageURL: String,
    var imageWidth: Int,
    var imageHeight: Int,
    var imageSize: Int,
    var views: Int,
    var downloads: Int,
    var collections: Int,
    var likes: Int,
    var comments: Int,
    var user: String,
    var userImageURL: String,
)