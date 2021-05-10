package com.goddoro.module.di.repositoryImpl

import com.goddoro.module.di.network.LoginAPI
import com.goddoro.module.di.repository.LoginRepository
import com.goddoro.module.model.User
import io.reactivex.Single


/**
 * created By DORO 5/10/21
 */

class LoginRepositoryImpl (private val api : LoginAPI) : LoginRepository {
    override suspend fun login(email: String, password: String): User {
        return api.login(email,password).unWrapData().user
    }


}