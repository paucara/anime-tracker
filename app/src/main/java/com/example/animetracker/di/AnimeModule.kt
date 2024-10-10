package com.example.animetracker.di

import com.example.animetracker.data.AnimeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AnimeModule {
    @Provides
    fun provideApiService(): AnimeService
    {
        return Retrofit.Builder()
            .baseUrl("https://api.trace.moe/")
            .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AnimeService::class.java)
    }


}