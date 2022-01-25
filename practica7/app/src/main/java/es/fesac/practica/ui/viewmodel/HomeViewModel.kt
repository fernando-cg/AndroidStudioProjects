package es.fesac.practica.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.practica.data.manager.RepositoryManager
import es.fesac.practica.ui.common.MAX_SQUARES
import es.fesac.practica.ui.common.MIN_SQUARES
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

            val mutablelevelList = mutableListOf<LevelVo>()
            for (i in MIN_SQUARES..MAX_SQUARES) {
                mutablelevelList.add(LevelVo(i.toLong(), "$i x $i", i, 0))
            }
            val levelList: List<LevelVo> =RepositoryManager.getLevels()?:mutablelevelList
            withContext(Dispatchers.Main) {
                loadLevelsMutableLiveData.value = levelList
                loadingMutableLiveData.value = false
            }
        }
    }

    fun isLoggedUser(): Boolean {
        return RepositoryManager.isLoggedUser()
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            val error = RepositoryManager.logout()

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                logoutMutableLiveData.value = error == null
                errorMutableLiveData.value = error
            }
        }
    }
}