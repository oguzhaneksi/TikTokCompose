package com.example.tiktokcompose.domain.repository

import com.example.tiktokcompose.domain.models.VideoData
import kotlinx.coroutines.flow.Flow

interface RedditDataRepository {
    fun getTikTokCringeVideos(): Flow<List<VideoData>>
}