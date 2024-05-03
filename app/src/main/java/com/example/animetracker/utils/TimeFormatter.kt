package com.example.animetracker.utils

fun formatterTime(seconds: Double): String {
    val hours = seconds.div(3600).toInt()
    val minutesRemaining = (seconds % 3600).toInt()
    val minutes = minutesRemaining.div(60)
    val secondsRemaining = minutesRemaining % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secondsRemaining)
}