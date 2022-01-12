package es.fesac.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel:ViewModel() {
    private val loadingMutableLiveData: MutableLiveData<Boolean> by lazy{
        MutableLiveData<Boolean>()
    }

    private val errorMutableLiveData: MutableLiveData<String?> by lazy{
        MutableLiveData<String?>()
    }

    private val successLoginMutableLiveData: MutableLiveData<Boolean> by lazy{
        MutableLiveData<Boolean>()
    }

    fun loadingLiveData() : LiveData<Boolean> = loadingMutableLiveData
    fun errorLiveData() : LiveData<String?> = errorMutableLiveData
    fun successLoginLiveData() : LiveData<Boolean> = successLoginMutableLiveData

    fun login(email:String,password:String){
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                loadingMutableLiveData.value = true
            }
            Firebase.auth.
            withContext(Dispatchers.Main){
                loadingMutableLiveData.value = false
            }
        }
    }
}