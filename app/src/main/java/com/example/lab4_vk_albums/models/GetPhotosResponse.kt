package com.example.lab4_vk_albums.models

import com.example.lab4_vk_albums.models.AlbumPhotos
import com.example.lab4_vk_albums.models.Errors
import com.google.gson.annotations.SerializedName

data class GetPhotosResponse(
    @SerializedName("response") var response: AlbumPhotos,
    @SerializedName("error") var error: Errors
)
