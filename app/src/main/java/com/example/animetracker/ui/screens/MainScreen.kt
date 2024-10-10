package com.example.animetracker.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.animetracker.utils.readContentUri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun MainScreen(
    viewModel: AnimeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { TopBar() }
    ) { paddingValues ->
        Content(
            paddingValues,
            viewModel.animeResult,
            viewModel.screenState,
            viewModel::getAnimeInfoByImage,
            viewModel::screenStateReset
        )
    }
}

@Composable
fun TopBar() {
    Row(Modifier.padding(15.dp)) {
        Text(text = "Anime Tracker", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    animeResult: AnimeResult,
    screenState: Boolean,
    getAnimeInfoByImage: (RequestBody?) -> Unit,
    screenStateReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (screenState) {
            Results(animeResult, screenStateReset)
        } else {
            ImageSelector(getAnimeInfoByImage)
        }
    }
}

@Composable
fun ImageSelector(getAnimeInfoByImage: (RequestBody?) -> Unit) {
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = {
            launcher.launch(
                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text(text = "Select Image")
        }
        result.value?.let { image ->
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = image)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp, 150.dp)
            )

            val bytes: ByteArray? = readContentUri(LocalContext.current, image)
            val requestBody = bytes?.toRequestBody("image/jpeg".toMediaTypeOrNull())

            TextButton(onClick = { getAnimeInfoByImage(requestBody) }) {
                Text(text = "Search")
            }
        }
    }
}
