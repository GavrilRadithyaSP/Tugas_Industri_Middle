package com.gavril.midapps.friend_app.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
data class FriendEntity(
    var name: String,
    var school: String,
    var hobby: String,
    @ColumnInfo(defaultValue = "")
    var phoneNumber: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}