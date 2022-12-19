package com.example.lab4_vk_albums.models.albumModel

import com.google.gson.annotations.SerializedName

data class UserAlbums(
    @SerializedName("count") var count: Int,
    @SerializedName("items") var items: List<Album>
)
