package es.fesac.practica.data.manager.score

import es.fesac.practica.data.manager.auth.AuthRepositoryManager

object ScoreRepositoryManager {
    suspend fun getCurrentScore(idLevel: String) = ScoreRemoteManager.getCurrentScore(idLevel, AuthRepositoryManager.getUser())

    suspend fun updateScore(idLevel: String, newScore: Long) =
        ScoreRemoteManager.updateScore(idLevel, newScore, AuthRepositoryManager.getUser())
}