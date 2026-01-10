package com.gavril.midapps.friend_app.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Delete
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.gavril.midapps.friend_app.database.dao.FriendDao
import com.gavril.midapps.friend_app.database.entity.FriendEntity

@Database(
    entities = [FriendEntity::class],
    version = 5,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(1,2),
        AutoMigration(2,3),
        AutoMigration(3,4, MyDatabase.RenameFriendNumberToNumberPhone::class),
        AutoMigration(4,5, MyDatabase.DeleteFriendBio::class)
    ]
)
abstract class MyDatabase: RoomDatabase() {
    abstract fun friendDao(): FriendDao
    @RenameColumn("friends", "phone", "phoneNumber")
    class RenameFriendNumberToNumberPhone: AutoMigrationSpec
    @DeleteColumn("friends","bio")
    class DeleteFriendBio: AutoMigrationSpec
    companion object{
        @Volatile
        private var INSTANCE: MyDatabase? = null
        fun getInstance(context: Context): MyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null ){
                return tempInstance
            }
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                "my_database"
            )
                .fallbackToDestructiveMigration(false)
                .build()
            INSTANCE = instance
            return instance
        }
    }
}