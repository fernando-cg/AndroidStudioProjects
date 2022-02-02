package es.fesac.practica.data.manager.levels

object LevelRepositoryManager {
    suspend fun getLevels() = LevelRemoteManager.getLevels()
}