package es.fesac.practica.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.practica.data.manager.auth.AuthRepositoryManager
import es.fesac.practica.data.manager.levels.LevelRepositoryManager
import es.fesac.practica.ui.mapper.toVo
import es.fesac.practica.ui.model.LevelVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val loadingMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val logoutMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val errorMutableLiveData: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    private val loadLevelsMutableLiveData: MutableLiveData<List<LevelVo>> by lazy {
        MutableLiveData<List<LevelVo>>()
    }

    fun getLoadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun getLogoutLiveData(): LiveData<Boolean> = logoutMutableLiveData
    fun getErrorLiveData(): LiveData<String?> = errorMutableLiveData
    fun getLoadLevelsLiveData(): LiveData<List<LevelVo>> = loadLevelsMutableLiveData

    fun loadLevels() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }
            val user = AuthRepositoryManager.getUser()
            val levelBoList = LevelRepositoryManager.getLevels() ?: emptyList()
            val levelVoList = levelBoList.map { bo ->
                bo.toVo()
            }
            user?.let {
                levelVoList.forEach { vo ->
                    vo.record = user.recordMap[vo.id.toString()] ?: 0
                }
            }
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                loadLevelsMutableLiveData.value = levelVoList
            }
        }
    }

    fun isLoggedUser(): Boolean {
        return AuthRepositoryManager.isLoggedUser()
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            val error = AuthRepositoryManager.logout()

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                logoutMutableLiveData.value = error == null
                errorMutableLiveData.value = error
            }
        }
    }
}