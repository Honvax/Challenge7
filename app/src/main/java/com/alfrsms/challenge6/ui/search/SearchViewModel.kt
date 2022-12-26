package com.alfrsms.challenge6.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfrsms.challenge6.data.network.model.search.SearchItem
import com.alfrsms.challenge6.data.repository.MovieRepository
import com.alfrsms.challenge6.wrapper.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {

    private val _searchResult = MutableLiveData<Resource<List<SearchItem>>>()
    val searchResult: LiveData<Resource<List<SearchItem>>> = _searchResult

    fun searchMovie(query: String) {
        Log.d("searchMovie", "searchMovie")
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.searchMovie(query)
            viewModelScope.launch(Dispatchers.Main) {
                _searchResult.postValue(data)
            }
        }
    }
}
