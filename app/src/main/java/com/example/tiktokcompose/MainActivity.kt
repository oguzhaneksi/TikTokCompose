package com.example.tiktokcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokcompose.ui.composables.TikTokScreen
import com.example.tiktokcompose.ui.theme.TikTokComposeTheme
import com.example.tiktokcompose.viewmodel.TikTokViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TikTokViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TikTokComposeTheme {
                TikTokScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

