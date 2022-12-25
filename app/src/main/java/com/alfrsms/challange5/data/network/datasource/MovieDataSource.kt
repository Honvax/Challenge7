package com.alfrsms.challange5.data.network.datasource

import com.alfrsms.challange5.data.network.model.detail.DetailMovie
import com.alfrsms.challange5.data.network.model.popular.Popular
import com.alfrsms.challange5.data.network.model.search.Search
import com.alfrsms.challange5.data.network.model.toprated.TopRated
import com.alfrsms.challange5.data.network.model.upcoming.Upcoming
import com.alfrsms.challange5.data.network.service.MovieApiService

interface MovieDataSource {
    suspend fun getPopular(): Popular
    suspend fun getTopRated(): TopRated
    suspend fun getUpcoming(): Upcoming
    suspend fun searchMovie(query: String): Search
    suspend fun getDetail(id: Int): DetailMovie
}

class MovieRemoteDataSourceImpl(private val apiService: MovieApiService): MovieDataSource {
    override suspend fun getPopular(): Popular {
        return apiService.getPopular()
    }

    override suspend fun getTopRated(): TopRated {
        return apiService.getTopRated()
    }

    override suspend fun getUpcoming(): Upcoming {
        return apiService.getUpcoming()
    }

    override suspend fun searchMovie(query: String): Search {
        return apiService.searchMovie(query = query)
    }

    override suspend fun getDetail(id: Int): DetailMovie {
        return apiService.getDetail(id)
    }

}