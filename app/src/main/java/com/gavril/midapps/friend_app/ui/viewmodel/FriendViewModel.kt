package com.gavril.midapps.friend_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.gavril.midapps.friend_app.base.BaseViewModel
import com.gavril.midapps.friend_app.database.dao.FriendDao
import com.gavril.midapps.friend_app.database.entity.FriendEntity
import com.gavril.midapps.friend_app.database.repo.friend.FriendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendDao: FriendDao,
    private val friendRepository: FriendRepository,): BaseViewModel() {
    private val _friends = MutableSharedFlow<List<FriendEntity>>()
    val friends = _friends.asSharedFlow()
//    fun getFriends() = friendDao.getAllFriends()
    fun getFriends() = viewModelScope.launch {
        friendRepository.getAllFriends().collect {
            _friends.emit(it)
        }
    }
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