package com.alfrsms.challange5.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfrsms.challange5.data.local.database.user.UserEntity
import com.alfrsms.challange5.data.repository.UserRepository
import com.alfrsms.challange5.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private var _getIfUserExistResult = SingleLiveEvent<Boolean>()
    val getIfUserExistResult: SingleLiveEvent<Boolean> get() = _getIfUserExistResult

    fun registerUser(user: UserEntity) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    fun getIfUserExist(username: String){
        viewModelScope.launch {
            _getIfUserExistResult.value = repository.getIfUserExists(username)
        }
    }
}