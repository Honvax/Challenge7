package com.alfrsms.challenge6.ui.login

import androidx.lifecycle.*
import com.alfrsms.challenge6.data.local.preference.PreferenceUser
import com.alfrsms.challenge6.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel(){

    fun getUser(): LiveData<PreferenceUser> {
        return repository.getUser().asLiveData()
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            repository.setUserLogin(isLogin)
        }
    }

    fun getUserLogin(): LiveData<Boolean> {
        return repository.getUserLogin().asLiveData()
    }

    fun registerUser(user: PreferenceUser) {
        viewModelScope.launch {
            repository.setUser(user)
        }
    }
}