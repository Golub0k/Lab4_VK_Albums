package com.example.lab4_vk_albums.models.photosModel

import com.example.lab4_vk_albums.models.commonModel.Errors
import com.google.gson.annotations.SerializedName

data class GetPhotosResponse(
    @SerializedName("response") var response: AlbumPhotos,
    @SerializedName("error") var error: Errors
)
