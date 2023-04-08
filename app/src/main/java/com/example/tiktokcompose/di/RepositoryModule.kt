package com.example.tiktokcompose.di

import com.example.tiktokcompose.data.api.RedditApi
import com.example.tiktokcompose.data.repository.RedditDataRepository
import com.example.tiktokcompose.data.repository.SampleVideoDataRepository
import com.example.tiktokcompose.domain.repository.VideoDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    @Named("reddit_data")
    fun provideRedditDataRepository(
        api: RedditApi
    ): VideoDataRepository {
        return RedditDataRepository(api)
    }

    @Provides
    @Singleton
    @Named("sample_data")
    fun provideSampleVideoDataRepository(): VideoDataRepository {
        return SampleVideoDataRepository()
    }
}