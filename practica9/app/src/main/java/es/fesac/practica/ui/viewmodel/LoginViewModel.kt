package es.fesac.practica.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.practica.R
import es.fesac.practica.common.MyApplication
import es.fesac.practica.data.manager.auth.AuthRepositoryManager
import kotlinx.coroutines.*

class LoginViewModel : ViewModel() {
    private val loadingMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val loginMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val errorMutableLiveData: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    fun getLoadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun getLoginLiveData(): LiveData<Boolean> = loginMutableLiveData
    fun getErrorLiveData(): LiveData<String?> = errorMutableLiveData

    fun login(user: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            //sleep(10000)
            var error = validateFields(user, password)
            if (error == null) {
                error = AuthRepositoryManager.login(user, password)
            }

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                loginMutableLiveData.value = error == null
                errorMutableLiveData.value = error
            }
        }
    }

    private fun validateFields(user: String, password: String): String? {
        return when {
            user.isBlank() -> {
                MyApplication.instance.getString(R.string.error_loading__empty_user)
            }
            password.isBlank() -> {
                MyApplication.instance.getString(R.string.error_loading__empty_password)
            }
            else -> {
                null
            }
        }
    }
}