package com.dguitarclassic.githubuserapps2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Favorite::class],
    version = 1
)

abstract class DbUser: RoomDatabase() {
    companion object{
        var INSTANCE : DbUser? = null
        fun getDatabase(context: Context): DbUser?{
            if(INSTANCE == null) {
                synchronized(DbUser::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DbUser::class.java, "db_user").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteDao(): FavoriteDao
}