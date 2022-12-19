package com.example.lab4_vk_albums.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val apiKey = "391d944b391d944b391d944b693a0c79bc3391d391d944b5a829eac8b3655e49e7eb8fd"
    private const val version = "5.131"


        fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor { chain ->
                            val url = chain
                                .request()
                                .url()
                                .newBuilder()
                                .addQueryParameter("v", version)
                                .addQueryParameter("access_token", apiKey)
                                .build()
                            chain.proceed(chain.request().newBuilder().url(url).build())
                        }
                        .build()
                )
                .build()
        }
    }