package com.example.tiktokcompose.data.api

import com.example.tiktokcompose.data.models.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {
    @GET("/r/tiktokcringe/{sort}.json?raw_json=1")
    suspend fun tikTokCringe(
        @Path("sort") sort: String? = "top",
        @Query("t") top: String? = "today"
    ): RedditResponse
}