package com.example.tiktokcompose.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedditResponse(
    val data: Data1?
) {
    @JsonClass(generateAdapter = true)
    data class Data1(
        @Json(name = "children")
        val posts: List<Post>?
    ) {
        @JsonClass(generateAdapter = true)
        data class Post(
            val data: Data2?
        ) {
            @JsonClass(generateAdapter = true)
            data class Data2(
                val id: String,
                @Json(name = "secure_media")
                val secureMedia: SecureMedia?,
                val preview: Preview?
            ) {
                @JsonClass(generateAdapter = true)
                data class SecureMedia(
                    @Json(name = "reddit_video")
                    val video: Video?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Video(
                        val width: Int?,
                        val height: Int?,
                        val duration: Int?,
                        @Json(name = "hls_url")
                        val hlsUrl: String?,
                        @Json(name = "dash_url")
                        val dashUrl: String?
                    )
                }

                @JsonClass(generateAdapter = true)
                data class Preview(
                    val images: List<Image>?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Image(
                        val source: Source?
                    ) {
                        @JsonClass(generateAdapter = true)
                        data class Source(
                            val url: String?,
                            val width: Int?,
                            val height: Int?
                        )
                    }
                }
            }
        }
    }
}
