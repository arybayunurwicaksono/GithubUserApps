package com.dguitarclassic.githubuserapps2.view_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val id : Int,
    val login : String,
    val avatar_url : String
) : Parcelable