package es.fesac.practica.data.manager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

/**
 * TODO 6.1:
 * Implementar los métodos de:
 *  - login(email: String, password: String): String?. Se llamará al método de login con email y contraseña de Firebase, en caso de error, se retornará la excepción que retorne dicho método.
 *  - register(password: String, email: String): String?. Se llamará al método de registro con email y contraseña de Firebase , en caso de error, se retornará la excepción que retorne dicho método.
 *  - logout(): String?. Se llamará al método de logout de Firebase , en caso de error, se retornará la excepción que retorne dicho método.
 */
object RemoteManager {
    fun isLoggedUser() = FirebaseAuth.getInstance().currentUser != null

    suspend fun login(email: String, password: String): String? {
        // TODO 6.1: Implementar método de login
        return try{
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            return null
        } catch (exception: Exception){
            exception.localizedMessage
        }
    }

    suspend fun register(password: String, email: String): String? {
        // TODO 6.1: Implementar método de registro
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            null
        }catch (exception : Exception){
            exception.localizedMessage
        }
    }

    suspend fun logout(): String? {
        // TODO 6.1: Implementar método de logout
        return try {
            Firebase.auth.signOut()
            null
        }catch (exception: Exception){
            exception.localizedMessage
        }
    }
}