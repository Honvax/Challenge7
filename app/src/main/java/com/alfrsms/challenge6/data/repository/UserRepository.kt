package com.alfrsms.challenge6.data.repository

import com.alfrsms.challenge6.data.local.preference.PreferenceUser
import com.alfrsms.challenge6.data.local.preference.UserPreferenceDataSource
import com.alfrsms.challenge6.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    suspend fun setUser(user: PreferenceUser)
    suspend fun updateUser(user: PreferenceUser)
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setProfileImage(image: String)

    fun getUser(): Flow<PreferenceUser>
    fun getUserLogin(): Flow<Boolean>
}

class UserRepositoryImpl @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
): UserRepository {
    override suspend fun setUser(user: PreferenceUser) {
        userPreferenceDataSource.setUser(user)
    }

    override suspend fun updateUser(user: PreferenceUser) {
        userPreferenceDataSource.updateUser(user)
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        userPreferenceDataSource.setUserLogin(isLogin)
    }

    override suspend fun setProfileImage(image: String) {
        userPreferenceDataSource.setProfileImage(image)
    }

    override fun getUser(): Flow<PreferenceUser> {
        return userPreferenceDataSource.getUser()
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userPreferenceDataSource.getUserLogin()
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}