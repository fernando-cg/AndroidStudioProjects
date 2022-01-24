package es.fesac.practica.data.manager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object RemoteManager {
    /**
     * TODO 7.1. En base al usuario recibido por parámetros se sacará su email
     * y posteriormente se intentará el login con email y contraseña de
     * Firebase, en caso de error, se retornará la excepción que retorne
     * dicho método.
     */
    suspend fun loginWithUser(userName: String, password: String): String? {

    }

    /**
     * TODO 7.2. Se comprobará si el usuario no existe, en caso de que no exista
     * se creará en la colección de “users” con la información que se ve en la
     * imagen. Posteriormente se realizará un registro con email y contraseña
     * de Firebase, en caso de error, se retornará la excepción que retorne
     * dicho método.
     */
    suspend fun register(email: String, user: String, password: String): String? {

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
}