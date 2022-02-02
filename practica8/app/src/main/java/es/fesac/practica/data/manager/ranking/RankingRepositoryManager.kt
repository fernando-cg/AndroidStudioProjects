package es.fesac.practica.data.manager.ranking

import es.fesac.practica.model.UserRankingBo

object RankingRepositoryManager {
    /**
     * TODO 8.2 RankingRepositoryManager
     * Implementar el método de:
     * getRankingList(): List<UserRankingBo>?. Retornará el listado obtenido por el método
     * getRankingList proporcionado por RankingRemoteManager.
     * Este método es el que usará el ViewModel.
     */
    suspend fun getRankingList():List<UserRankingBo>? = RankingRemoteManager.getRankingList()
}