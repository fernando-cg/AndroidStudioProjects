package es.fesac.practica.data.manager

import es.fesac.practica.ui.extension.isEmail

object RepositoryManager {
    fun isLoggedUser() = RemoteManager.isLoggedUser()

    /**
     * TODO 7.1. Si el usuario es un email se debe llamar a login normal
     * en caso de no ser un email se debe llamar a loginWithUser.
     */
    suspend fun login(user: String, password: String): String? {

        return if(user.isEmail()){

            RemoteManager.login(email = user, password = password)

        }else{
            RemoteManager.loginWithUser(userName = user, password = password)
        }
    }

    suspend fun register(email: String, user: String, password: String) =
        RemoteManager.register(email, user, password)

    suspend fun logout() = RemoteManager.logout()
}