package es.fesac.practica.data.manager

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.fesac.practica.ui.common.COLLECTION_USERS
import es.fesac.practica.ui.common.USER_USERNAME
import kotlinx.coroutines.tasks.await
import es.fesac.practica.R
import es.fesac.practica.model.UserBo
import es.fesac.practica.model.toMap
import es.fesac.practica.ui.common.COLLECTION_LEVELS
import es.fesac.practica.ui.model.LevelVo

object RemoteManager {
    /**
     * TODO 7.1. En base al usuario recibido por parámetros se sacará su email
     * y posteriormente se intentará el login con email y contraseña de
     * Firebase, en caso de error, se retornará la excepción que retorne
     * dicho método.
     */

    //Chequear que se retornen los tipos de errores,
    suspend fun loginWithUser(context: Context, userName: String, password: String): String? {
        return try {
            val snapshot = Firebase.firestore
                .collection(COLLECTION_USERS)
                .whereEqualTo(USER_USERNAME, userName)
                .get()
                .await()
            if (snapshot.documentChanges.isEmpty()) {
                return context.getString(R.string.error_loading__fail_login)
            } else {
                val document = snapshot.documents.firstOrNull()
                 if (document != null) {
                    val userBo = document.toObject(UserBo::class.java)
                    if (userBo != null) {
                        Firebase.auth.signInWithEmailAndPassword(userBo.email, password).await()
                        null
                    } else {
                        context.getString(R.string.error_loading__fail_login)
                    }
                } else {
                    context.getString(R.string.error_loading__fail_login)
                }
            }
        }catch (exception: Exception) {
            exception.localizedMessage
        }
    }

    /**
     * TODO 7.2. Se comprobará si el usuario no existe, en caso de que no exista
     * se creará en la colección de “users” con la información que se ve en la
     * imagen. Posteriormente se realizará un registro con email y contraseña
     * de Firebase, en caso de error, se retornará la excepción que retorne
     * dicho método.
     */
    suspend fun register(context: Context,email: String, user: String, password: String): String? {
        return try {
            val snapshot = Firebase.firestore
                .collection(COLLECTION_USERS)
                .whereEqualTo(USER_USERNAME, user)
                .get()
                .await()
            if (snapshot.documents.isEmpty()) {
                Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                Firebase.auth.currentUser?.let { userLogged ->
                    val userToSave = UserBo(
                        id = userLogged.uid,
                        email = email,
                        user = user,
                        recordMap = mapOf("1" to 0, "2" to 0, "3" to 0, "4" to 0)
                    )
                    Firebase.firestore
                        .collection(COLLECTION_USERS)
                        .add(userToSave.toMap())
                        .await()
                }
                null
            } else {
                context.getString(R.string.register_error__not_available_user)
            }
        }catch (exception: Exception) {
            exception.localizedMessage
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

    suspend fun getLevels(): List<LevelVo>?{
         return try{
            val snapshot = Firebase.firestore
                .collection(COLLECTION_LEVELS)
                .get()
                .await()
             val documents = snapshot.documents
            val levels:MutableList<LevelVo> = mutableListOf()
            documents.forEach { document->
                document.toObject(LevelVo::class.java)?.let { levels.add(it) }
            }
            return levels
        }catch(exception: Exception){
            Log.e("error",exception.localizedMessage)
             null
        }

    }
}