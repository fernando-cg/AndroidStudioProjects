package es.fesac.practica.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.practica.R
import es.fesac.practica.common.MyApplication
import es.fesac.practica.data.manager.RepositoryManager
import es.fesac.practica.ui.extension.isEmail
import kotlinx.coroutines.Dispatchers
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

    fun register(context: Context, email: String, password: String, passwordRepeat: String, user: String, check: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            var error = validateFields(email, password, passwordRepeat, user, check)
            if (error == null) {
                error = RepositoryManager.register(context,email, user, password)
            }

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                registerMutableLiveData.value = error == null
                errorMutableLiveData.value = error
            }
        }
    }

    private fun validateEmail(email: String) = email.isEmail()

    private fun validatePassword(password: String) = password.isEmpty()

    private fun validateUser(user: String) = user.isEmpty()

    private fun validateFields(email: String, password: String, passwordRepeat: String, user: String, check: Boolean): String? {
        var error: String? = null
        if (validateEmail(email).not()) {
            error = MyApplication.instance.getString(R.string.register_error__empty_email)
        }
        if (validatePassword(password)) {
            error = MyApplication.instance.getString(R.string.register_error__empty_password)
        }
        if (password.equals(passwordRepeat, ignoreCase = false).not()) {
            error = MyApplication.instance.getString(R.string.register_error__password_repeat)
        }
        if (validateUser(user)) {
            error = MyApplication.instance.getString(R.string.register_error__empty_user)
        }
        if (check.not()) {
            error = MyApplication.instance.getString(R.string.register_error__accept_term_and_conditions)
        }
        return error
    }
}