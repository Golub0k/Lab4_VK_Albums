package com.example.lab4_vk_albums.api

import com.example.lab4_vk_albums.models.GetAlbumResponse
import com.example.lab4_vk_albums.models.GetPhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {
    @GET("photos.getAlbums")
    suspend fun getAlbums(
        @Query("owner_id") ownerId: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
        @Query("need_covers") needCovers: Int
    ): Response<GetAlbumResponse>

    @GET("photos.get")
    suspend fun getPhotos(
        @Query("owner_id") ownerId: String,
        @Query("album_id") albumId: Int,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
        @Query("photo_sizes") photoSizes: Int
    ): Response<GetPhotosResponse>
}