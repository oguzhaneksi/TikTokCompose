package com.example.tiktokcompose.data.repository

import android.util.Log
import com.example.tiktokcompose.data.api.RedditApi
import com.example.tiktokcompose.domain.models.VideoData
import com.example.tiktokcompose.domain.repository.RedditDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException

class RedditDataRepositoryImpl(
    private val api: RedditApi
): RedditDataRepository {
    override fun getTikTokCringeVideos(): Flow<List<VideoData>> = flow {
        try {
            val response = api.tikTokCringe()
            val videoData = response
                .data
                ?.posts
                ?.map { post ->
                    val video = post.data?.secureMedia?.video
                    val width = video?.width
                    val height = video?.height
                    val aspectRatio = if (width != null && height != null) {
                        width.toFloat() / height.toFloat()
                    } else {
                        null
                    }
                    VideoData(
                        id = post.data?.id.orEmpty(),
                        mediaUri = video?.hlsUrl.orEmpty(),
                        previewImageUri = post.data?.preview?.images?.firstOrNull()?.source?.url.orEmpty(),
                        aspectRatio = aspectRatio
                    )
                }
                ?.filter { videoData ->
                    videoData.id.isNotBlank()
                            && videoData.mediaUri.isNotBlank()
                            && videoData.previewImageUri.isNotBlank()
                }
                .orEmpty()

            emit(videoData)
        } catch (throwable: Throwable) {
            if (throwable is CancellationException) throw throwable
            Log.d("asdf", "Error", throwable)
        }
    }
}