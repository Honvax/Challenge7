package com.alfrsms.challenge6.di

import com.alfrsms.challenge6.data.local.preference.UserPreferenceDataSource
import com.alfrsms.challenge6.data.local.preference.UserPreferenceDataSourceImpl
import com.alfrsms.challenge6.data.network.datasource.MovieDataSource
import com.alfrsms.challenge6.data.network.datasource.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideMovieDataSource(movieRemoteDataSourceImpl: MovieDataSourceImpl): MovieDataSource

    @Binds
    abstract fun provideUserDataSource(userPreferenceDataSourceImpl: UserPreferenceDataSourceImpl): UserPreferenceDataSource
}