package es.fesac.practica.data.manager.auth

import es.fesac.practica.ui.extension.isEmail

object AuthRepositoryManager {
    fun isLoggedUser() = AuthRemoteManager.isLoggedUser()

    suspend fun login(user: String, password: String) =
        if (user.isEmail()) {
            AuthRemoteManager.login(user, password)
        } else {
            AuthRemoteManager.loginWithUser(user, password)
        }

    suspend fun register(email: String, user: String, password: String) =
        AuthRemoteManager.register(email, user, password)

    suspend fun logout() = AuthRemoteManager.logout()
}