package com.example.lab4_vk_albums.models.photosModel

import com.google.gson.annotations.SerializedName

data class AlbumPhotos(
    @SerializedName("count") var count: Int,
    @SerializedName("items") var items: List<Photo>
)
