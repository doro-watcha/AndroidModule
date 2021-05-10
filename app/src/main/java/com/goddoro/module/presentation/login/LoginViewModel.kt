package com.goddoro.module.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.module.di.repository.LoginRepository
import com.goddoro.module.model.User
import com.goddoro.module.utils.Once
import kotlinx.coroutines.launch


/**
 * created By DORO 5/10/21
 */

class LoginViewModel (
    private val loginRepository : LoginRepository
) : ViewModel() {

    val email : MutableLiveData<String> = MutableLiveData()
    val password : MutableLiveData<String> = MutableLiveData()

    val loginSuccess : MutableLiveData<Once<User>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()



    fun login () {

        viewModelScope.launch{

            kotlin.runCatching {
                loginRepository.login(email.value ?: "", password.value ?: "")
            }.onSuccess {
                loginSuccess.value = Once(it)
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
    }
}