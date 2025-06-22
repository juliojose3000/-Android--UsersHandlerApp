package com.loaizasoftware.usershandlerapp.di

import android.content.Context
import com.loaizasoftware.usershandlerapp.data.local.dao.UserDao
import com.loaizasoftware.usershandlerapp.data.local.database.AppDatabase
import com.loaizasoftware.usershandlerapp.data.repositories_impl.UserRepositoryImpl
import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module //A module provides dependencies
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUserDao(@ApplicationContext context: Context): UserDao  {
        return AppDatabase.getDatabase(context).userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

}