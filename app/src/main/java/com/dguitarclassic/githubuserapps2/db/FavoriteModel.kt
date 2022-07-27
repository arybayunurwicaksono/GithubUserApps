package com.dguitarclassic.githubuserapps2.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavoriteModel (application: Application): AndroidViewModel(application) {

    private var userDao: FavoriteDao?
    private var userDb: DbUser?

    init {
        userDb = DbUser.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }

    fun getFavorite(): LiveData<List<Favorite>>? {
        return userDao?.getFavoriteData()
    }
}