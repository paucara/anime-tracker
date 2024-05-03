package com.example.animetracker.model

data class AnimeData(
    val frameCount: Int,
    val error: String,
    val result: List<Result>
)

data class Result(
    val anilist: AniList,
    val filename: String,
    val episode: Any?,
    val from: Double,
    val to: Double,
    val similarity: Double,
    val video: String,
    val image: String
)

data class AniList(
    val id: Int,
    val idMal: Int,
    val title: Title,
    val synonyms: List<String>,
    val isAdult: Boolean
)

data class Title(
    val native: String,
    val romaji: String,
    val english: String?
)