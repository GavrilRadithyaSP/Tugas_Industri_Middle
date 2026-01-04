package com.gavril.midapps.friend_app.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gavril.midapps.friend_app.database.entity.FriendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {
    @Query("SELECT * FROM friends ORDER BY name ASC")
    fun getAllFriends(): Flow<List<FriendEntity>>
    @Query("SELECT * FROM friends")
    fun getAll() : Flow<List<FriendEntity>>
    @Query("SELECT * FROM friends WHERE id = :id")
    fun getFriendById(id: Int): Flow<FriendEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(friend: FriendEntity)
    @Update
    suspend fun update(friend: FriendEntity)
    @Delete
    suspend fun delete(friend: FriendEntity)
}