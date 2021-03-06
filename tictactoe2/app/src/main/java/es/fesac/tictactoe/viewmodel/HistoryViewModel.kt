package es.fesac.tictactoe.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.fesac.tictactoe.common.DB_NAME
import es.fesac.tictactoe.common.HISTORY_COLLECTION
import es.fesac.tictactoe.common.MyApplication
import es.fesac.tictactoe.common.SCORE_LIST_COLLECTION
import es.fesac.tictactoe.local.MyDatabase
import es.fesac.tictactoe.local.mapper.toBo
import es.fesac.tictactoe.local.mapper.toEntity
import es.fesac.tictactoe.model.ScoreBo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HistoryViewModel : ViewModel() {
    private val loadingMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    private val errorMutableLiveData: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }
    private val scoreListMutableLiveData: MutableLiveData<List<ScoreBo>> by lazy {
        MutableLiveData<List<ScoreBo>>()
    }
    fun getLoadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun getErrorLiveData(): LiveData<String?> = errorMutableLiveData
    fun getScoreListLiveData(): LiveData<List<ScoreBo>> = scoreListMutableLiveData

    fun loadScoreList() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            var scoreList: List<ScoreBo> = emptyList()
            val error = try {
                scoreList = getScoreList() ?: emptyList()
                null
            } catch (exception: Exception) {
                exception.localizedMessage
            }

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                errorMutableLiveData.value = error
                scoreListMutableLiveData.value = scoreList
            }
        }
    }

    private suspend fun getScoreList(): List<ScoreBo>? {
        return try {
            val db = Room.databaseBuilder(
                MyApplication.instance,
                MyDatabase::class.java,
                DB_NAME
            ).build()
            val scoreListFromLocal = db.getScoreDao().getAllScoreList()
            if (scoreListFromLocal.isEmpty()) {
                val snapshot = Firebase.firestore
                    .collection(HISTORY_COLLECTION)
                    .document(Firebase.auth.currentUser?.uid.toString())
                    .collection(SCORE_LIST_COLLECTION)
                    .get()
                    .await()
                val scoreListFromRemote = snapshot.documents.map { document ->
                    document.toObject(ScoreBo::class.java) ?: ScoreBo("", 0L)
                }
                db.getScoreDao().insertScores(scoreListFromRemote.map { bo ->
                    bo.toEntity()
                })
                scoreListFromRemote
            } else {
                scoreListFromLocal.map { entity ->
                    entity.toBo()
                }
            }
        } catch (exception: Exception) {
            Log.e(HistoryViewModel::class.java.simpleName, exception.localizedMessage, exception)
            null
        }
    }
}