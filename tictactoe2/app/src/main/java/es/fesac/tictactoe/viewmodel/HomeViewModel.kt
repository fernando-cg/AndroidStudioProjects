package es.fesac.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val multiplayerMutableLiveData = MutableLiveData<Boolean>()
    private val loadingMutableLiveData = MutableLiveData<Boolean>()

    val multiplayerLiveData: LiveData<Boolean> = multiplayerMutableLiveData
    val loadingLiveData: LiveData<Boolean> = loadingMutableLiveData

    fun getMultiplayerState() {
        viewModelScope.launch(Dispatchers.IO) {
            multiplayerMutableLiveData.postValue(false)

            delay(4000)

            multiplayerMutableLiveData.postValue(true)
        }
    }

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingMutableLiveData.postValue(true)

            delay(4000)

            loadingMutableLiveData.postValue(false)
        }
    }
}