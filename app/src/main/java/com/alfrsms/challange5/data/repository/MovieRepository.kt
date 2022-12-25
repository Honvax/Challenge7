package com.alfrsms.challange5.data.repository

import com.alfrsms.challange5.data.network.datasource.MovieDataSource
import com.alfrsms.challange5.data.network.model.MovieItem
import com.alfrsms.challange5.data.network.model.detail.DetailMovie
import com.alfrsms.challange5.data.network.model.search.Search
import com.alfrsms.challange5.wrapper.Resource

interface MovieRepository {
    suspend fun getPopular(): Resource<List<MovieItem>>
    suspend fun getTopRated(): Resource<List<MovieItem>>
    suspend fun getUpcoming(): Resource<List<MovieItem>>
    suspend fun searchMovie(query: String): Resource<Search>
    suspend fun getDetail(id: Int): Resource<DetailMovie>
}

class MovieRepositoryImpl(private val dataSource: MovieDataSource): MovieRepository {
    override suspend fun getPopular(): Resource<List<MovieItem>> {
        return proceed {
            dataSource.getPopular().results?.map {
                MovieItem(
                    adult = it.adult,
                    backdropPath = it.backdropPath,
                    genreIds = it.genreIds,
                    id = it.id,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    overview = it.overview,
                    popularity = it.popularity,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    video = it.video,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount
                ) }!!
        }
    }

    override suspend fun getTopRated(): Resource<List<MovieItem>> {
        return proceed {
            dataSource.getTopRated().results?.map {
                MovieItem(
                    adult = it.adult,
                    backdropPath = it.backdropPath,
                    genreIds = it.genreIds,
                    id = it.id,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    overview = it.overview,
                    popularity = it.popularity,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    video = it.video,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount
                ) }!!
        }
    }

    override suspend fun getUpcoming(): Resource<List<MovieItem>> {
        return proceed {
            dataSource.getUpcoming().results?.map {
                MovieItem(
                    adult = it.adult,
                    backdropPath = it.backdropPath,
                    genreIds = it.genreIds,
                    id = it.id,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    overview = it.overview,
                    popularity = it.popularity,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    video = it.video,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount
                ) }!!
        }
    }

    override suspend fun searchMovie(query: String): Resource<Search> {
        return try {
            val data = dataSource.searchMovie(query)
            if (data.results.isNullOrEmpty()) Resource.Empty() else Resource.Success(data)
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

    override suspend fun getDetail(id: Int): Resource<DetailMovie> {
        return proceed {
            dataSource.getDetail(id)
        }
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}