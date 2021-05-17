package com.goddoro.module.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 5/17/21
 */

@Parcelize
data class ImageResponse(

    @SerializedName("success")
    val success : Boolean,

    @SerializedName("prediction")
    val predictions : List<Float>,

    @SerializedName("message")
    val messages : List<String>
) : Parcelable