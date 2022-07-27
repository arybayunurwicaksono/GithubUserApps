package com.dguitarclassic.githubuserapps2.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dguitarclassic.githubuserapps2.config.Client
import com.dguitarclassic.githubuserapps2.view_model.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<UserModel>>()

    fun setSearch (query:String){
        Client.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }else{
                        Log.d(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure; ${t.message.toString()}")
                }

            })
    }

    fun getSearch(): LiveData<ArrayList<UserModel>> {
        return listUser
    }

    companion object{
        const val TAG = "MainModel"
    }
}