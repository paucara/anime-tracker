package com.example.animetracker.ui.screens

sealed interface AnimeResult {
    data class Success(val result: List<AnimeItem>) : AnimeResult
    data object Error : AnimeResult
    data object Loading : AnimeResult
}
