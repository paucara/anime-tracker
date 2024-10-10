package com.example.animetracker.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animetracker.data.AnimeService
import com.example.animetracker.utils.formatterTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeService: AnimeService
) : ViewModel() {

    var animeResult: AnimeResult by mutableStateOf(AnimeResult.Loading)

    var screenState by mutableStateOf(false)

    fun getAnimeInfoByImage(request: RequestBody?) {
        screenState = true
        viewModelScope.launch {
            animeResult = AnimeResult.Loading
            animeResult = try {
                val response = animeService.search(request)
                AnimeResult.Success(
                    result = response.result.map {
                        AnimeItem(
                            title = it.anilist.title.romaji,
                            episode = it.episode?.toString(),
                            from = formatterTime(it.from),
                            image = it.image,
                            isAdult = it.anilist.isAdult
                        )
                    }
                )
            } catch (e: IOException) {
                AnimeResult.Error
            } catch (e: IllegalArgumentException) {
                AnimeResult.Error
            } catch (e : Exception){
                AnimeResult.Error
            }
        }
    }

    fun screenStateReset() {
        screenState = false
    }
}
