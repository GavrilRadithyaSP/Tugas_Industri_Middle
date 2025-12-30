package com.example.mvvm.di_dependency_injection.modul

import android.content.Context
import com.example.mvvm.session.PrefsMiddleApps
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

}