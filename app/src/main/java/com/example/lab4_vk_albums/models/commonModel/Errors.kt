package com.example.lab4_vk_albums.models.commonModel

import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("error_code") val code: Int
)
