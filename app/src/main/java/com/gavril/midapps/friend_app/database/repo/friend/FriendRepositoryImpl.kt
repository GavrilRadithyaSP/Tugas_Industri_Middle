package com.gavril.midapps.friend_app.database.repo.friend

import com.gavril.midapps.friend_app.database.dao.FriendDao
import com.gavril.midapps.friend_app.database.entity.FriendEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(private val friendDao: FriendDao): FriendRepository {
    override suspend fun getAllFriends(): Flow<List<FriendEntity>> {
        return friendDao.getAllFriends()
    }
    override suspend fun getAll(): Flow<List<FriendEntity>> {
        return friendDao.getAll()
    }
    override suspend fun getAllFriendById(id: Int): Flow<FriendEntity> {
        return friendDao.getFriendById(id)
    }
}