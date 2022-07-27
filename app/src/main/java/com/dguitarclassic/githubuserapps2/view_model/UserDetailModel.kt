package com.dguitarclassic.githubuserapps2.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dguitarclassic.githubuserapps2.db.DbUser
import com.dguitarclassic.githubuserapps2.db.Favorite
import com.dguitarclassic.githubuserapps2.db.FavoriteDao
import com.dguitarclassic.githubuserapps2.config.Client
import com.dguitarclassic.githubuserapps2.view_model.response.UserDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailModel (application: Application) : AndroidViewModel(application){

    val userDetail = MutableLiveData<UserDetailResponse>()

    private var userDao: FavoriteDao?
    private var userDb: DbUser?

    init {
        userDb = DbUser.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }

    fun setDetail (username : String){

        Client.apiInstance
            .getDetail(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful){
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }

            })
    }

    fun addtoFavorite(username: String, id: Int, avatar: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = Favorite(
                username,
                id,
                avatar
            )
            userDao?.addToFavorite(user)
        }
    }
    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

    fun getDetail() : LiveData<UserDetailResponse> {
        return userDetail
    }
}