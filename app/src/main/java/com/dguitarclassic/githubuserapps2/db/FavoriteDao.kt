package com.dguitarclassic.githubuserapps2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    fun addToFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    fun getFavoriteData(): LiveData<List<Favorite>>

    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun removeFromFavorite(id: Int): Int
}