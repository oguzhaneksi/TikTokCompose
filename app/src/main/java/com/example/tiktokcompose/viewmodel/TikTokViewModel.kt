package com.example.tiktokcompose.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.example.tiktokcompose.domain.repository.RedditDataRepository
import com.example.tiktokcompose.ui.state.VideoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TikTokViewModel @Inject constructor(
    private val repository: RedditDataRepository
): ViewModel() {

    private val _state = MutableStateFlow(VideoUiState())
    val state: StateFlow<VideoUiState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTikTokCringeVideos().collect {
                _state.emit(VideoUiState(
                    videos = it
                ))
            }
        }
    }

    fun createPlayer(context: Context) {
        _state.value = state.value.copy(player = ExoPlayer.Builder(context).build())
    }

    fun releasePlayer() {
        state.value.player?.release()
        _state.value = state.value.copy(player = null)
    }
}