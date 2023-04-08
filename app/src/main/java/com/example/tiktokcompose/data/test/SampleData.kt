package com.example.tiktokcompose.data.test

import com.example.tiktokcompose.domain.models.VideoData

object SampleData {
    private val video1 = VideoData(
        id = "1",
        mediaUri = "https://assets.mixkit.co/videos/preview/mixkit-tree-with-yellow-flowers-1173-large.mp4",
        previewImageUri = "https://mixkit.imgix.net/videos/preview/mixkit-tree-with-yellow-flowers-1173-0.jpg",
        aspectRatio = .5625f
    )

    private val video2 = VideoData(
        id = "2",
        mediaUri = "https://assets.mixkit.co/videos/preview/mixkit-palm-tree-in-front-of-the-sun-1191-large.mp4",
        previewImageUri = "https://mixkit.imgix.net/videos/preview/mixkit-palm-tree-in-front-of-the-sun-1191-0.jpg",
        aspectRatio = .5625f
    )

    private val video3 = VideoData(
        id = "3",
        mediaUri = "https://assets.mixkit.co/videos/preview/mixkit-red-frog-on-a-log-1487-large.mp4",
        previewImageUri = "https://mixkit.imgix.net/videos/preview/mixkit-red-frog-on-a-log-1487-0.jpg",
        aspectRatio = .5625f
    )

    val sampleVideos = listOf(video1, video2, video3)
}