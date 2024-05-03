package com.example.animetracker.ui.screens

data class AnimeItem(
    val title : String,
    val episode : String?,
    val from : String,
    val image : String,
    val isAdult : Boolean
)