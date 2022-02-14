package es.fesac.practica.data.manager.ranking

import android.util.Log
import androidx.room.Room
import es.fesac.practica.common.DB_NAME
import es.fesac.practica.common.MyApplication
import es.fesac.practica.data.manager.levels.LevelLocalManager
import es.fesac.practica.local.MyDatabase
import es.fesac.practica.local.mapper.toBo
import es.fesac.practica.local.mapper.toEntity
import es.fesac.practica.model.LevelBo
import es.fesac.practica.model.UserRankingBo

/**
 * TODO 9.7: Implementar el siguiente método:
 * - getRankingList(): List<UserRankingBo>?
 * - saveRankingList(rankingFromRemote: List<UserRankingBo>)
 */
object RankingLocalManager{
    suspend fun getRankingList():List<UserRankingBo>?{
        return try{
            val db = Room.databaseBuilder(
                MyApplication.instance,
                MyDatabase::class.java,
                DB_NAME
            ).build()
            val rankingList = db.getUserRankingDao().getAllUserRankingList()
            if (rankingList.isEmpty()) {
                val remoterankingList = RankingRemoteManager.getRankingList() //Preguntar esto y cambiar
                remoterankingList?.let { RankingLocalManager.saveRankingList(it) }
                remoterankingList
            }else{
                rankingList.map { entity ->
                    entity.toBo()
                }
            }
        }catch (exception: Exception){
            Log.e("excepcion","Se ha producido un error")
            null
        }
    }

    suspend fun saveRankingList(boList:List<UserRankingBo>){
        val db = Room.databaseBuilder(
            MyApplication.instance,
            MyDatabase::class.java,
            DB_NAME).build()

        boList.let {
            db.getUserRankingDao().insertUserRankings( it.map { bo ->
                bo.toEntity()
            })
        }
    }
}