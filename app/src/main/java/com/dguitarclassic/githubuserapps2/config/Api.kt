package com.dguitarclassic.githubuserapps2.config

import com.dguitarclassic.githubuserapps2.view_model.UserModel
import com.dguitarclassic.githubuserapps2.view_model.response.UserDetailResponse
import com.dguitarclassic.githubuserapps2.view_model.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail (
        @Path("username") username : String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollower (
        @Path("username") username : String
    ): Call<ArrayList<UserModel>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing (
        @Path("username") username : String
    ): Call<ArrayList<UserModel>>
}