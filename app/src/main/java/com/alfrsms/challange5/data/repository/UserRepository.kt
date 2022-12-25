package com.alfrsms.challange5.data.repository

import com.alfrsms.challange5.data.local.database.user.UserDataSource
import com.alfrsms.challange5.data.local.database.user.UserEntity
import com.alfrsms.challange5.data.local.preference.UserPreferenceDataSource
import com.alfrsms.challange5.wrapper.Resource

interface UserRepository {
    fun checkIfUserLoggedIn(): Boolean
    fun setIfUserLogin(userLoggedIn: Boolean)

    fun getUserId(): Long
    fun setUserId(id: Long)

    suspend fun registerUser(user: UserEntity): Resource<Number>
    suspend fun updateUser(user: UserEntity): Resource<Number>
    suspend fun checkIsUserLoginValid(username: String, password: String): Boolean
    suspend fun getIfUserExists(username: String): Boolean
    suspend fun getUserById(id: Long): UserEntity
    suspend fun getUserByUsername(username: String): UserEntity
}

class UserRepositoryImpl(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val userDataSource: UserDataSource,
): UserRepository {
    override fun checkIfUserLoggedIn(): Boolean {
        return userPreferenceDataSource.getIfUserLogin()
    }

    override fun setIfUserLogin(userLoggedIn: Boolean){
        return userPreferenceDataSource.setIfUserLogin(userLoggedIn)
    }

    override fun getUserId(): Long {
        return userPreferenceDataSource.getUserId()
    }

    override fun setUserId(id: Long) {
        return userPreferenceDataSource.setUserId(id)
    }

    override suspend fun registerUser(user: UserEntity): Resource<Number> {
        return proceed {
            userDataSource.registerUser(user)
        }
    }

    override suspend fun updateUser(user: UserEntity): Resource<Number> {
        return proceed {
            userDataSource.updateUser(user)
        }
    }

    override suspend fun checkIsUserLoginValid(username: String, password: String): Boolean {
        return userDataSource.validateUserLogin(username, password)
    }

    override suspend fun getIfUserExists(username: String): Boolean {
        return userDataSource.getIfUserExists(username)
    }

    override suspend fun getUserById(id: Long): UserEntity {
        return userDataSource.getUserById(id)
    }

    override suspend fun getUserByUsername(username: String): UserEntity {
        return userDataSource.getUserByUsername(username)
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}