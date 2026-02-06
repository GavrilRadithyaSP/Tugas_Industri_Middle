package com.gavril.midapps.di_dependency_injection.modul

import com.gavril.midapps.friend_app.database.repo.friend.FriendRepository
import com.gavril.midapps.friend_app.database.repo.friend.FriendRepositoryImpl
import com.gavril.midapps.login_app.repo.DataProductRepo
import com.gavril.midapps.login_app.repo.DataProductRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {
    @Singleton
    @Binds
    abstract fun bindFriendRepository(impFriendRepository: FriendRepositoryImpl): FriendRepository
    @Singleton
    @Binds
    abstract fun bindDataProductRepo(implDataProductRepo: DataProductRepoImpl): DataProductRepo
}