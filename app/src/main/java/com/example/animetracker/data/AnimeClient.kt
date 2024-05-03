package com.example.animetracker.data

import com.example.animetracker.model.AnimeData
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface AnimeService {
    @POST("search?anilistInfo")
    suspend fun search(@Body imagesBody: RequestBody?): AnimeData
}

object AnimeClient {
    private const val BASE_URL = "https://api.trace.moe/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val animeService: AnimeService = retrofit.create(AnimeService::class.java)
}
