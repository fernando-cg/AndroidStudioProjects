package es.fesac.practica4.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.practica4.ui.common.MAX_SQUARES
import es.fesac.practica4.ui.common.MIN_SQUARES
import es.fesac.practica4.ui.model.LevelVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    // TODO: 4.3 ViewModel
    private val loadLevelsMutableLiveData = MutableLiveData<List<LevelVo>>()
    val loadLevelsLiveData:LiveData<List<LevelVo>> = loadLevelsMutableLiveData

    fun loadLevels() {
        viewModelScope.launch(Dispatchers.IO) {
            val levelList = mutableListOf<LevelVo>()
            for (i in MIN_SQUARES..MAX_SQUARES) {
                levelList.add(LevelVo(i.toLong(), "$i x $i", i, 0))
            }
            withContext(Dispatchers.Main) {
                loadLevelsMutableLiveData.value = levelList
            }
        }
    }
}