package com.example.tiktokcompose.domain.repository

import com.example.tiktokcompose.domain.models.VideoData
import kotlinx.coroutines.flow.Flow

interface VideoDataRepository {
    fun fetchData(): Flow<List<VideoData>>
}