package es.fesac.practica.data.manager.ranking

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.fesac.practica.common.COLLECTION_RANKING
import es.fesac.practica.model.UserRankingBo
import kotlinx.coroutines.tasks.await
import java.lang.Exception

object RankingRemoteManager {
    /**
     * TODO 8.1 RankingRemoteManager
     * Implementar el método de:
     * getRankingList(): List<UserRankingBo>?. Se debe obtener el listado de usuarios
     * bajo la colección “ranking”.
     */
    suspend fun getRankingList(): List<UserRankingBo>? {
        return try{
            val snapshot = Firebase.firestore
                .collection(COLLECTION_RANKING)
                .get()
                .await()
            var ranking : List<UserRankingBo> = snapshot.documents.map { document ->
                document.toObject(UserRankingBo::class.java)?: UserRankingBo("0",
                    "0",
                    0,
                    "0")
            }
            ranking
        }catch (exception:Exception){
            Log.e("RankingRemoteManager","it was an error: $exception")

            null
        }
    }
}