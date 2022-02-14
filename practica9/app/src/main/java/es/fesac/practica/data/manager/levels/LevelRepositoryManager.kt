package es.fesac.practica.data.manager.levels

import es.fesac.practica.model.LevelBo

object LevelRepositoryManager {
    suspend fun getLevels(): List<LevelBo>? {
        val levelsFromLocal = LevelLocalManager.getLevels()
        return if (levelsFromLocal.isNullOrEmpty()) {
            val levelFromRemote = LevelRemoteManager.getLevels()
            levelFromRemote?.let {
                LevelLocalManager.saveLevelList(levelFromRemote)
            }
            levelFromRemote
        } else {
            levelsFromLocal
        }
    }
}