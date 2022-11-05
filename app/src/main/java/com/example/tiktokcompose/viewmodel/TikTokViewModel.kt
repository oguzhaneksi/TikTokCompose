package com.example.tiktokcompose.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.tiktokcompose.domain.repository.RedditDataRepository
import com.example.tiktokcompose.ui.effect.AnimationEffect
import com.example.tiktokcompose.ui.effect.PlayerErrorEffect
import com.example.tiktokcompose.ui.effect.ResetAnimationEffect
import com.example.tiktokcompose.ui.effect.VideoEffect
import com.example.tiktokcompose.ui.state.VideoUiState
import com.example.tiktokcompose.util.toMediaItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TikTokViewModel @Inject constructor(
    private val repository: RedditDataRepository
): ViewModel() {

    private val _state = MutableStateFlow(VideoUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<VideoEffect>()
    val effect = _effect.asSharedFlow()

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
        if (state.value.player == null) {
            _state.value = state.value.copy(player = ExoPlayer.Builder(context).build().apply {
                repeatMode = Player.REPEAT_MODE_ONE
                setMediaItems(state.value.videos.toMediaItems())
                prepare()
            })
        }
    }

    fun releasePlayer(isChangingConfigurations: Boolean) {
        if (isChangingConfigurations)
            return
        state.value.player?.release()
        _state.value = state.value.copy(player = null)
    }

    fun onPlayerError() {
        viewModelScope.launch(Dispatchers.Main) {
            state.value.player?.let { player ->
                _effect.emit(PlayerErrorEffect(
                    message = player.playerError?.message.toString(),
                    code = player.playerError?.errorCode ?: -1)
                )
            }
        }
    }

    fun onTappedScreen() {
        viewModelScope.launch(Dispatchers.Main) {
            _effect.emit(ResetAnimationEffect)
            state.value.player?.let { player ->
                val drawable = if (player.isPlaying) {
                    player.pause()
                    com.example.tiktokcompose.R.drawable.pause
                }
                else {
                    player.play()
                    com.example.tiktokcompose.R.drawable.play
                }
                _effect.emit(AnimationEffect(drawable))
            }
        }
    }
}