package com.gavril.midapps.friend_app.database.repo.friend

import com.gavril.midapps.friend_app.database.entity.FriendEntity
import kotlinx.coroutines.flow.Flow

interface FriendRepository {
    suspend fun getAllFriends(): Flow<List<FriendEntity>>
    suspend fun getAll(): Flow<List<FriendEntity>>
    suspend fun getAllFriendById(id: Int): Flow<FriendEntity>
}