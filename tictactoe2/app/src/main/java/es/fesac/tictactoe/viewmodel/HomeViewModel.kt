package es.fesac.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.fesac.tictactoe.common.APP_SETTINGS_COLLECTION
import es.fesac.tictactoe.model.AppSettingsBo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val appSettingsMutableLiveData = MutableLiveData<AppSettingsBo?>()
    private val loadingMutableLiveData = MutableLiveData<Boolean>()
    private val logoutSuccessMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    val appSettingsLiveData: LiveData<AppSettingsBo?> = appSettingsMutableLiveData
    val loadingLiveData: LiveData<Boolean> = loadingMutableLiveData

    fun logoutSuccessLiveData(): LiveData<Boolean> = logoutSuccessMutableLiveData

    fun getAppSettingsState() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            var appSettings: AppSettingsBo? = null
            val snapshot = Firebase.firestore
                .collection(APP_SETTINGS_COLLECTION)
                .document("1")
                .get()
                .await()
            snapshot?.let { document ->
                appSettings = document.toObject(AppSettingsBo::class.java)
            }

            withContext(Dispatchers.Main) {
                appSettingsMutableLiveData.value = appSettings
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