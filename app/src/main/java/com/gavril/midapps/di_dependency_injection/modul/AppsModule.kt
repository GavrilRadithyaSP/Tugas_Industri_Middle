package com.gavril.midapps.di_dependency_injection.modul

import android.content.Context
import com.crocodic.core.data.CoreSession
import com.crocodic.core.helper.NetworkHelper
import com.gavril.midapps.friend_app.database.MyDatabase
import com.gavril.midapps.friend_app.database.dao.FriendDao
import com.gavril.midapps.login_app.api.ApiService
import com.gavril.midapps.session.PrefsMiddleApps
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppsModule {
    @Singleton
    @Provides
    fun providePrefsMiddleApps(@ApplicationContext context: Context) = PrefsMiddleApps(context)
    @Singleton
    @Provides
    fun provideCoreSession(@ApplicationContext context: Context) = CoreSession(context)
    @Singleton
    @Provides
    fun provideGson() = Gson()
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase = MyDatabase.getInstance(context)
    @Singleton
    @Provides
    fun provideFriendDao(database: MyDatabase) = database.friendDao()
    @Singleton
    @Provides
    fun provideApiService(): ApiService{
        return NetworkHelper.provideApiService(
            baseUrl = "https://dummyjson.com/",
            okHttpClient = NetworkHelper.provideOkHttpClient(),
            converterFactory = listOf(GsonConverterFactory.create())
        )
    }
    fun providesOkHttp(session: CoreSession): OkHttpClient{
        return NetworkHelper.provideOkHttpClient().newBuilder().apply {
            addInterceptor {
                val original = it.request()
                val requestBuilder = original.newBuilder()
                    .header("Content-Type","application/json")
                    .method(original.method,original.body)
                val token = session.getString(CoreSession.PREF_UID)
                if (token.isNotEmpty()){
                    requestBuilder.header("Authorization","Bearer $token")
                }
                val request = requestBuilder.build()
                it.proceed(request)
            }
        }.build()
    }
}