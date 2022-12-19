package com.example.lab4_vk_albums.models.albumModel

import com.example.lab4_vk_albums.models.commonModel.Errors
import com.google.gson.annotations.SerializedName

data class GetAlbumResponse(
    @SerializedName("response") var response: UserAlbums,
    @SerializedName("error") var error: Errors
)