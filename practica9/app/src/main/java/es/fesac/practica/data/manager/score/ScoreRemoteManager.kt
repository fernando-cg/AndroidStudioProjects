package es.fesac.practica.data.manager.score

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.fesac.practica.common.COLLECTION_USERS
import es.fesac.practica.model.UserBo
import kotlinx.coroutines.tasks.await

object ScoreRemoteManager {
    suspend fun getCurrentScore(idLevel: String, user: UserBo?): Long {
        return user?.recordMap?.get(idLevel) ?: 0
    }

    suspend fun updateScore(idLevel: String, newScore: Long, user: UserBo?): String? {
        return try {
            user?.let {
                user.recordMap[idLevel] = newScore
                Firebase.firestore
                    .collection(COLLECTION_USERS)
                    .document(user.id)
                    .set(user.toMap(), SetOptions.merge())
                    .await()
            }
            null
        } catch (e: Exception) {
            e.localizedMessage
        }
    }
}