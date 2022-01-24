package es.fesac.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val multiplayerMutableLiveData = MutableLiveData<Boolean>()
    private val loadingMutableLiveData = MutableLiveData<Boolean>()
    private val logoutSuccessMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    val multiplayerLiveData: LiveData<Boolean> = multiplayerMutableLiveData
    val loadingLiveData: LiveData<Boolean> = loadingMutableLiveData

    fun logoutSuccessLiveData(): LiveData<Boolean> = logoutSuccessMutableLiveData

    fun getMultiplayerState() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                multiplayerMutableLiveData.value = false
                loadingMutableLiveData.value = true
            }

            delay(4000)

            withContext(Dispatchers.Main) {
                multiplayerMutableLiveData.value = true
                loadingMutableLiveData.value = false
            }
        }
    }

    fun isUserLogged(): Boolean {
        return Firebase.auth.currentUser != null
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            Firebase.auth.signOut()

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                logoutSuccessMutableLiveData.value = true
            }
        }
    }
}