package com.dguitarclassic.githubuserapps2.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dguitarclassic.githubuserapps2.config.Client
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PagerModel: ViewModel(){

    val followers = MutableLiveData<ArrayList<UserModel>>()
    val following = MutableLiveData<ArrayList<UserModel>>()

    fun setFollowing(username: String){
        Client.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<UserModel>> {
                override fun onResponse(
                    call: Call<ArrayList<UserModel>>,
                    response: Response<ArrayList<UserModel>>
                ) {
                    if(response.isSuccessful){
                        following.postValue(response.body())
                    }

                }

                override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }
            })
    }

    fun getFollowing() : LiveData<ArrayList<UserModel>> {
        return following
    }

    fun setFollowers(username: String){
        Client.apiInstance
            .getFollower(username)
            .enqueue(object : Callback<ArrayList<UserModel>> {
                override fun onResponse(
                    call: Call<ArrayList<UserModel>>,
                    response: Response<ArrayList<UserModel>>
                ) {
                    if (response.isSuccessful){
                        followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }

            })
    }

    fun getFollowers() : LiveData<ArrayList<UserModel>> {
        return followers
    }

}