package es.fesac.practica.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.practica.data.manager.score.ScoreRepositoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel : ViewModel() {
    private val loadLevelHighScoreMutableLiveData: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    private val loadScreenMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getLoadLevelHighScoreData(): LiveData<Long> = loadLevelHighScoreMutableLiveData
    fun getLoadScreenData(): LiveData<Boolean> = loadScreenMutableLiveData

    fun loadHighScore(idLevel: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                loadScreenMutableLiveData.value = true
            }
            val record = ScoreRepositoryManager.getCurrentScore(idLevel)
            withContext(Dispatchers.Main){
                loadLevelHighScoreMutableLiveData.value = record
                loadScreenMutableLiveData.value = false
            }
        }
    }

    fun updateScore(newScore: Long, idLevel: String){
        viewModelScope.launch(Dispatchers.IO) {
            val currentRecord = ScoreRepositoryManager.getCurrentScore(idLevel)
            if (currentRecord < newScore) {
                ScoreRepositoryManager.updateScore(idLevel, newScore)
                withContext(Dispatchers.Main){
                    loadLevelHighScoreMutableLiveData.value = newScore
                }
            }
        }
    }
}