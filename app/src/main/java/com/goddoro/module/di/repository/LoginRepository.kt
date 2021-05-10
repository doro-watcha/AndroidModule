package com.goddoro.module.di.repository

import com.goddoro.module.model.User
import io.reactivex.Single


/**
 * created By DORO 5/10/21
 */

interface LoginRepository {


    suspend fun login (
        email : String,
        password : String
    ) : User
}