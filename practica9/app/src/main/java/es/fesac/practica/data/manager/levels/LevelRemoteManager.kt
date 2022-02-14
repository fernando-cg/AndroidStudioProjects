package es.fesac.practica.data.manager.levels

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.fesac.practica.common.COLLECTION_LEVELS
import es.fesac.practica.model.LevelBo
import kotlinx.coroutines.tasks.await

object LevelRemoteManager {
    suspend fun getLevels(): List<LevelBo>? {
        return try {
            val querySnapshot = Firebase.firestore
                .collection(COLLECTION_LEVELS)
                .get()
                .await()
            querySnapshot.toObjects(LevelBo::class.java)
        } catch (exception: Exception) {
            Log.e(LevelRemoteManager::class.java.simpleName, exception.message, exception)
            null
        }
    }
}