package com.alfrsms.challange5.di

import android.content.Context
import com.alfrsms.challange5.data.network.datasource.MovieDataSource
import com.alfrsms.challange5.data.network.datasource.MovieRemoteDataSourceImpl
import com.alfrsms.challange5.data.network.service.MovieApiService
import com.alfrsms.challange5.data.repository.MovieRepository
import com.alfrsms.challange5.data.repository.MovieRepositoryImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor

object MovieServiceLocator {

    private fun provideChucker(appContext: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(appContext).build()
    }

    private fun provideApiService(chuckerInterceptor: ChuckerInterceptor): MovieApiService {
        return MovieApiService.invoke(chuckerInterceptor)
    }

    private fun provideDataSource(apiService: MovieApiService): MovieDataSource {
        return MovieRemoteDataSourceImpl(apiService)
    }

    fun provideMovieRepository(context: Context): MovieRepository {
        return MovieRepositoryImpl(provideDataSource(provideApiService(provideChucker(context))))
    }
}