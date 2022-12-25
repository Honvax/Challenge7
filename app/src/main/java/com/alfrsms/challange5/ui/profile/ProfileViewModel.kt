package com.alfrsms.challange5.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfrsms.challange5.data.local.database.user.UserEntity
import com.alfrsms.challange5.data.repository.UserRepository
import com.alfrsms.challange5.wrapper.Resource
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    private val _updateResult = MutableLiveData<Resource<Number>>()
    val updateResult: LiveData<Resource<Number>> get() = _updateResult

    private val _userByIdResult = MutableLiveData<UserEntity>()
    val userByIdResult: LiveData<UserEntity> get() = _userByIdResult

    fun getUserId(): Long {
        return repository.getUserId()
    }

    fun getUserById(id: Long) {
        viewModelScope.launch {
            _userByIdResult.postValue(repository.getUserById(id))
        }
    }

    fun updateUser(note: UserEntity) {
        viewModelScope.launch {
            _updateResult.value = repository.updateUser(note)
        }
    }

    fun setIfUserLogin(userLoggedIn: Boolean){
        return repository.setIfUserLogin(userLoggedIn)
    }
}