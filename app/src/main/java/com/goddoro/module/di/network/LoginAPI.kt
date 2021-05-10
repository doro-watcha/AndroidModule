package com.goddoro.module.di.network

import android.os.Parcelable
import com.goddoro.module.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * created By DORO 5/10/21
 */

interface LoginAPI {


    @POST("auth/signin")
    @FormUrlEncoded
    suspend fun login (
        @Field("email") email : String,
        @Field("password") password : String
    ) : ApiResponse<LoginResponse>

}


@Parcelize
data class LoginResponse(
    @SerializedName("user")
    val user: User,

    @SerializedName("token")
    val token: String
) : Parcelable
