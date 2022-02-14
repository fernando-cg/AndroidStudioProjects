package es.fesac.practica.data.manager.ranking

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.fesac.practica.common.COLLECTION_RANKING
import es.fesac.practica.data.manager.levels.LevelRemoteManager
import es.fesac.practica.model.UserRankingBo
import kotlinx.coroutines.tasks.await

object RankingRemoteManager {
    suspend fun getRankingList(): List<UserRankingBo>? {
        return try {
            val querySnapshot = Firebase.firestore
                .collection(COLLECTION_RANKING)
                .get()
                .await()
            querySnapshot.toObjects(UserRankingBo::class.java)
        } catch (exception: Exception) {
            Log.e(LevelRemoteManager::class.java.simpleName, exception.message, exception)
            null
        }
    }
}