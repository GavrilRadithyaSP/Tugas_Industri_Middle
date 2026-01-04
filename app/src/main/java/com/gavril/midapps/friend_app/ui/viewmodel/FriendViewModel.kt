package com.gavril.midapps.friend_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.gavril.midapps.friend_app.database.dao.FriendDao
import com.gavril.midapps.friend_app.database.entity.FriendEntity

class FriendViewModel(private val friendDao: FriendDao): ViewModel() {
    fun getFriends() = friendDao.getAllFriends()
    fun getFriendsById(id: Int) = friendDao.getFriendById(id)
    suspend fun insertFriend(data: FriendEntity){
        friendDao.insert(data)
    }
    suspend fun updateFriend(data: FriendEntity){
        friendDao.update(data)
    }
    suspend fun deleteFriend(data: FriendEntity){
        friendDao.delete(data)
    }
}