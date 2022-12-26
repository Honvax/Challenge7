package com.alfrsms.challenge6.ui.home

import androidx.lifecycle.*
import com.alfrsms.challenge6.data.local.preference.PreferenceUser
import com.alfrsms.challenge6.data.network.model.HomeMovie
import com.alfrsms.challenge6.data.repository.MovieRepository
import com.alfrsms.challenge6.data.repository.UserRepository
import com.alfrsms.challenge6.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository, private val userRepository: UserRepository): ViewModel() {

    private val _homeMovieListResult = MutableLiveData<Resource<List<HomeMovie>>>()
    val homeMovieListResult: LiveData<Resource<List<HomeMovie>>> get() = _homeMovieListResult

    init {
        getHomeMovieList()
    }

    private fun getHomeMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeMovieListResult.postValue(Resource.Loading())
            //delay(1000)
            val popular = movieRepository.getPopular()
            val topRated = movieRepository.getTopRated()
            val upcoming = movieRepository.getUpcoming()

            val homeMovieList = mutableListOf<HomeMovie>()
            homeMovieList.add(HomeMovie(title = "Popular", results = popular.payload))
            homeMovieList.add(HomeMovie(title = "Top Rated", results = topRated.payload))
            homeMovieList.add(HomeMovie(title = "Upcoming", results = upcoming.payload))
            viewModelScope.launch(Dispatchers.Main) {
                _homeMovieListResult.postValue(Resource.Success(homeMovieList))
            }
        }
    }

    fun getUser(): LiveData<PreferenceUser> {
        return userRepository.getUser().asLiveData()
    }
}