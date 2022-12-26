package com.alfrsms.challenge6.di

import com.alfrsms.challenge6.data.repository.MovieRepository
import com.alfrsms.challenge6.data.repository.MovieRepositoryImpl
import com.alfrsms.challenge6.data.repository.UserRepository
import com.alfrsms.challenge6.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}