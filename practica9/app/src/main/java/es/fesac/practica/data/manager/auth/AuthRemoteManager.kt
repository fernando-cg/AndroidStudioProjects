package es.fesac.practica.data.manager.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import es.fesac.practica.common.*
import es.fesac.practica.model.UserBo
import kotlinx.coroutines.tasks.await

object AuthRemoteManager {

    suspend fun loginWithUser(userName: String, password: String): String? {
        return try {
            val querySnapshot = Firebase.firestore
                .collection(COLLECTION_USERS)
                .whereEqualTo(USER_USERNAME, userName)
                .get()
                .await()
            val user = querySnapshot.toObjects(UserBo::class.java).firstOrNull()
            if (user == null) {
                "El usuario no existe"
            } else {
                login(user.email, password)
            }
        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    suspend fun register(email: String, user: String, password: String): String? {
        return try {
            val querySnapshot = Firebase.firestore
                .collection(COLLECTION_USERS)
                .whereEqualTo(USER_USERNAME, user)
                .get()
                .await()

            if (querySnapshot.documents.isEmpty()) {
                Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                Firebase.auth.currentUser?.let { firebaseUser ->
                    val recordMap = hashMapOf<String, Long>()
                    val userInfoMap = hashMapOf(
                        USER_EMAIL to email,
                        USER_USERNAME to user,
                        USER_ID to firebaseUser.uid,
                        USER_RECORD_MAP to recordMap
                    )
                    // Cuenta creada correctamente
                    Firebase.firestore.collection(COLLECTION_USERS)
                        .document(firebaseUser.uid)
                        .set(userInfoMap)
                        .await()
                }
                null
            } else {
                "El usuario ya existe"
            }
        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    fun isLoggedUser() = FirebaseAuth.getInstance().currentUser != null

    suspend fun login(email: String, password: String): String? {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            null
        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    suspend fun logout(): String? {
        return try {
            Firebase.auth.signOut()
            null
        } catch (e: Exception) {
            e.localizedMessage
        }
    }

    suspend fun getUser(): UserBo? {
        return Firebase.auth.currentUser?.let { firebaseUser ->
            getUserById(firebaseUser.uid)
        }
    }

    private suspend fun getUserById(uid: String): UserBo? {
        return try {
            val querySnapshot = Firebase.firestore
                .collection(COLLECTION_USERS)
                .whereEqualTo(USER_ID, uid)
                .get()
                .await()
            val documentToUser = querySnapshot.documents.firstOrNull()
            val user = documentToUser?.toObject<UserBo>()
            user?.apply {
                id = documentToUser.id
            }
        } catch (e: Exception) {
            null
        }
    }
}