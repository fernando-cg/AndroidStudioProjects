package es.fesac.practica.data.manager.ranking

import es.fesac.practica.model.UserRankingBo

object RankingRepositoryManager {
    suspend fun getRankingList(): List<UserRankingBo>? {
        val rankingFromLocal = RankingLocalManager.getRankingList()
        return if (rankingFromLocal.isNullOrEmpty()) {
            val rankingFromRemote = RankingRemoteManager.getRankingList()
            rankingFromRemote?.let {
                RankingLocalManager.saveRankingList(rankingFromRemote)
            }
            rankingFromRemote
        } else {
            rankingFromLocal
        }
    }
}