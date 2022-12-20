package com.example.lab4_vk_albums.models

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id") var id: Int,
    @SerializedName("owner_id") var ownerId: Int,
    @SerializedName("title") var title: String,
    @SerializedName("thumb_id") var thumbId: Int,
    @SerializedName("thumb_src") var thumbSrc: String,
)
