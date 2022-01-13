package es.fesac.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val multiplayerMutableLiveData = MutableLiveData<Boolean>()
    private val loadingMutableLiveData = MutableLiveData<Boolean>()

    val multiplayerLiveData: LiveData<Boolean> = multiplayerMutableLiveData
    val loadingLiveData: LiveData<Boolean> = loadingMutableLiveData

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
}