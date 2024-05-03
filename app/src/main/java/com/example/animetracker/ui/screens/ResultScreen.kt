package com.example.animetracker.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.animetracker.R

@Composable
fun Results(animeResult: AnimeResult, screenStateReset: () -> Unit) {
    when (animeResult) {
        is AnimeResult.Loading -> LoadingScreen()
        is AnimeResult.Success -> ResultScreen(animeResult.result, screenStateReset)
        is AnimeResult.Error -> ErrorScreen(screenStateReset)
    }
}

@Composable
fun ResultScreen(animeResult: List<AnimeItem>, screenStateReset: () -> Unit) {

    val clipboardManager = LocalClipboardManager.current

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Results: ", modifier = Modifier
                .weight(5F)
                .padding(start = 15.dp), fontWeight = FontWeight.Medium
        )
        TextButton(onClick = { screenStateReset() }, modifier = Modifier.weight(1F)) {
            Text(text = "Back")
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(animeResult) { item ->
            ResultItem(item,clipboardManager)
        }
    }
}

@Composable
fun ResultItem(item: AnimeItem, clipboardManager: ClipboardManager) {

    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = item.image)
            .build(),
        placeholder = painterResource(R.drawable.loading_image)
    )
    Column(Modifier.padding(10.dp)) {

        val text = item.title

        Text(text = text, modifier = Modifier.clickable {
            clipboardManager.setText(AnnotatedString(text))
        })

        Divider()
        Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
            Column(modifier = Modifier
                .weight(3F)) {
                if(item.episode == null){
                    Text(text = "No information about the episode")
                }else{
                    Text(text = "Episode ${item.episode}")
                }
                Text(text = item.from)
            }

            if (item.isAdult) {
                Image(
                    painter = painterResource(id = R.drawable.censorship_image),
                    contentDescription = null,
                    modifier = Modifier.weight(3F)
                )
            } else {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.weight(3F)
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(screenStateReset: () -> Unit) {
    Text(
        text = "An error has occurred",
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 10.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.error_image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
    )
    TextButton(onClick = { screenStateReset() }) {
        Text(text = "Back")
    }
}

@Composable
fun LoadingScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        label = "",
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )
    Text(
        text = "Loading...",
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 10.dp)
    )
    Box{
        Image(
            painter = painterResource(id = R.drawable.loading_screen_image),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer(rotationZ = angle)
        )
    }
}

