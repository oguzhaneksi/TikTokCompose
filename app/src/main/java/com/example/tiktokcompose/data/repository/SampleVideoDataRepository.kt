package com.example.tiktokcompose.data.repository

import com.example.tiktokcompose.data.test.SampleData
import com.example.tiktokcompose.domain.models.VideoData
import com.example.tiktokcompose.domain.repository.VideoDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SampleVideoDataRepository: VideoDataRepository {
    override fun fetchData(): Flow<List<VideoData>> = flowOf(SampleData.sampleVideos)
}