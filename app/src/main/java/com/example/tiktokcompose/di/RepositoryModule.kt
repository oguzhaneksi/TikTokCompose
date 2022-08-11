package com.example.tiktokcompose.di

import com.example.tiktokcompose.data.api.RedditApi
import com.example.tiktokcompose.data.repository.RedditDataRepositoryImpl
import com.example.tiktokcompose.domain.repository.RedditDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRedditDataRepository(
        api: RedditApi
    ): RedditDataRepository {
        return RedditDataRepositoryImpl(api)
    }
}