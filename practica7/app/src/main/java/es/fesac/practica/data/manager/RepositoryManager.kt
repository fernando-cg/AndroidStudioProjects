package es.fesac.practica.data.manager

import android.content.Context
import android.util.Log
import es.fesac.practica.ui.extension.isEmail
import es.fesac.practica.ui.model.LevelVo

object RepositoryManager {
    fun isLoggedUser() = RemoteManager.isLoggedUser()

    /**
     * TODO 7.1. Si el usuario es un email se debe llamar a login normal
     * en caso de no ser un email se debe llamar a loginWithUser.
     */
    suspend fun login(context: Context, user: String, password: String): String? {

        return if(user.isEmail()){

            RemoteManager.login(email = user, password = password)

        }else{
            RemoteManager.loginWithUser(userName = user, password = password, context = context)
        }
    }

    suspend fun getLevels():List<LevelVo>?{
        val lista: List<LevelVo>? = RemoteManager.getLevels()
        Log.e("Errooorr",lista.toString())
        return lista
    }

    suspend fun register(context: Context,email: String, user: String, password: String) =
        RemoteManager.register(context,email, user, password)

    suspend fun logout() = RemoteManager.logout()
}