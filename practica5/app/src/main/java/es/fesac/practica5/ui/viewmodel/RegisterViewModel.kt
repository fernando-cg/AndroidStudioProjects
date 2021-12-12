package es.fesac.practica5.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.fesac.practica5.ui.extension.isEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {
    private val loadingMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val registerMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val errorMutableLiveData: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    fun getLoadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun getRegisterLiveData(): LiveData<Boolean> = registerMutableLiveData
    fun getErrorLiveData(): LiveData<String?> = errorMutableLiveData

    fun register(user: String, password: String, email: String) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                registerMutableLiveData.value = false
                errorMutableLiveData.value = ""
            }
        }
    }

    fun validateEmail(email: String) = email.isEmail()

    fun validatePassword(password: String) = password.isEmpty()

    fun validateUser(user: String) = user.isEmpty()
}