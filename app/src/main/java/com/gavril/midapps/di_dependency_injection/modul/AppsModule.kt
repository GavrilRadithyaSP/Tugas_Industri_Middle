package com.gavril.midapps.di_dependency_injection.modul

import android.content.Context
import com.gavril.midapps.friend_app.database.MyDatabase
import com.gavril.midapps.friend_app.database.dao.FriendDao
import com.gavril.midapps.session.PrefsMiddleApps
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppsModule {
    @Singleton
    @Provides
    fun providePrefsMiddleApps(@ApplicationContext context: Context) = PrefsMiddleApps(context)
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase = MyDatabase.getInstance(context)
    @Singleton
    @Provides
    fun provideFriendDao(database: MyDatabase) = database.friendDao()
}