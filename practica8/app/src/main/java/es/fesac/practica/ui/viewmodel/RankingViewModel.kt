package es.fesac.practica.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.practica.data.manager.ranking.RankingRepositoryManager
import es.fesac.practica.model.UserRankingBo
import es.fesac.practica.ui.mapper.toVo
import es.fesac.practica.ui.model.UserRankingVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

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

    /**
     * TODO 8.6:
     * Se debe implementar el método de “loadRanking”, no es necesario crear ningún LiveData
     * adicional. Dicho método debe cargar el listado de usuarios del RankingRepositoryManager,
     * transformarlo a UserRankingVo (usando la función toVo() de UserRankingMapper) y ordenar el
     * listado para que aparezcan primero los usuarios con mayor puntuación.
     * Finalmente, el listado se retornará al fragment.
     */
    fun loadRanking() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }
            var scoreVisualList : List<UserRankingVo>? = emptyList()

            val error = try{
                var scoreList: List<UserRankingBo>? = RankingRepositoryManager.getRankingList()
                 scoreVisualList = scoreList?.map{ element ->
                    element.toVo()
                }
                scoreVisualList?.sortedByDescending { element ->
                    element.score
                }
                null
            }catch (exception : Exception){
                exception.localizedMessage
            }

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                errorMutableLiveData.value = error
                rankingListMutableLiveData.value = scoreVisualList
            }
        }
    }
}