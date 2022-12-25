package com.alfrsms.challange5.di

import android.content.Context
import com.alfrsms.challange5.data.local.database.AppDatabase
import com.alfrsms.challange5.data.local.database.user.UserDao
import com.alfrsms.challange5.data.local.database.user.UserDataSource
import com.alfrsms.challange5.data.local.database.user.UserLocalDataSourceImpl
import com.alfrsms.challange5.data.local.preference.UserPreference
import com.alfrsms.challange5.data.local.preference.UserPreferenceDataSource
import com.alfrsms.challange5.data.local.preference.UserPreferenceDataSourceImpl
import com.alfrsms.challange5.data.repository.UserRepository
import com.alfrsms.challange5.data.repository.UserRepositoryImpl


object UserServiceLocator {

    private fun provideUserPreference(context: Context): UserPreference {
        return UserPreference(context)
    }

    private fun provideUserPreferenceDataSource(context: Context): UserPreferenceDataSource {
        return UserPreferenceDataSourceImpl(provideUserPreference(context))
    }

    private fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    private fun provideUserDao(context: Context): UserDao {
        return provideAppDatabase(context).userDao()
    }

    private fun provideUserDataSource(context: Context): UserDataSource {
        return UserLocalDataSourceImpl(provideUserDao(context))
    }

    fun provideUserRepository(context: Context): UserRepository {
        return UserRepositoryImpl(
            provideUserPreferenceDataSource(context),
            provideUserDataSource(context)
        )
    }
}