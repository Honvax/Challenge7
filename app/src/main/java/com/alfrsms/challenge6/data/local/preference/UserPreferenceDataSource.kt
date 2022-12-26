package com.alfrsms.challenge6.data.local.preference

import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

interface UserPreferenceDataSource {
    suspend fun setUser(user: PreferenceUser)
    suspend fun updateUser(user: PreferenceUser)
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setProfileImage(image: String)

    fun getUser(): Flow<PreferenceUser>
    fun getUserLogin(): Flow<Boolean>
}

class UserPreferenceDataSourceImpl @Inject constructor(
    private val userDataStore: UserDataStoreManager
): UserPreferenceDataSource {
    override suspend fun setUser(user: PreferenceUser) {
        userDataStore.setUser(user)
    }

    override suspend fun updateUser(user: PreferenceUser) {
        userDataStore.updateUser(user)
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        userDataStore.setUserLogin(isLogin)
    }

    override suspend fun setProfileImage(image: String) {
        userDataStore.setProfileImage(image)
    }

    override fun getUser(): Flow<PreferenceUser> {
        return userDataStore.getUser()
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userDataStore.getUserLogin()
    }
}