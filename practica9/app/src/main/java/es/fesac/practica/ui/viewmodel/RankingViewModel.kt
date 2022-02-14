package es.fesac.practica.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.practica.data.manager.ranking.RankingRepositoryManager
import es.fesac.practica.ui.mapper.toVo
import es.fesac.practica.ui.model.UserRankingVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RankingViewModel : ViewModel() {
    private val loadingMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val rankingListMutableLiveData: MutableLiveData<List<UserRankingVo>> by lazy {
        MutableLiveData<List<UserRankingVo>>()
    }

    private val errorMutableLiveData: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }
    fun getLoadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun getRankingLiveData(): LiveData<List<UserRankingVo>> = rankingListMutableLiveData
    fun getErrorLiveData(): LiveData<String?> = errorMutableLiveData

    fun loadRanking() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            val rankingBoList = RankingRepositoryManager.getRankingList() ?: emptyList()
            val rankingVoList = rankingBoList.map { bo ->
                bo.toVo()
            }.sortedByDescending {
                it.score
            }

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                rankingListMutableLiveData.value = rankingVoList
            }
        }
    }
}