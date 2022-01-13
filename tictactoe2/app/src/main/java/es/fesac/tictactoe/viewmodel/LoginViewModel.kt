package es.fesac.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class LoginViewModel : ViewModel() {
    private val loadingMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    private val errorMutableLiveData by lazy {
        MutableLiveData<String?>()
    }
    private val successLoginMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    fun loadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun errorLiveData(): LiveData<String?> = errorMutableLiveData
    fun successLoginLiveData(): LiveData<Boolean> = successLoginMutableLiveData

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }
           val error= try {
                Firebase.auth.signInWithEmailAndPassword(email, password).await()
            }catch (exception: Exception){
                exception.localizedMessage
            }
            val loginSuccess = error == null
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                successLoginMutableLiveData.value = loginSuccess
                errorMutableLiveData.value = error.toString()
            }
        }
    }
}