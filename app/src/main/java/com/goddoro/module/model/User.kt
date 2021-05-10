package com.goddoro.module.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 5/10/21
 */

@Parcelize
data class User(
    @SerializedName("id")
    val id : Int,

    @SerializedName("email")
    val email : String,

    @SerializedName("name")
    val name : String
) : Parcelable