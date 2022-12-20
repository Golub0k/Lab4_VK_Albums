package com.example.lab4_vk_albums.models

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("sizes") var sizes: List<PhotoUrl>
)