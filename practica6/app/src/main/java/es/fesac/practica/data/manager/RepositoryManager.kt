package es.fesac.practica.data.manager

object RepositoryManager {
    fun isLoggedUser() = RemoteManager.isLoggedUser()

    suspend fun login(email: String, password: String) =
        RemoteManager.login(email, password)

    suspend fun register(password: String, email: String) =
        RemoteManager.register(password, email)

    suspend fun logout() = RemoteManager.logout()
}