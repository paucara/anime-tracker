package com.example.animetracker.utils

import android.content.Context
import android.net.Uri

fun readContentUri(context: Context, uri: Uri): ByteArray? {
    return try {
        context.contentResolver.openInputStream(uri)?.readBytes()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
