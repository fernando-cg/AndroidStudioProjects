package es.fesac.practica.local.dao

import androidx.room.*
import es.fesac.practica.local.entity.LevelEntity
import es.fesac.practica.local.entity.UserRankingEntity

/**
 * TODO 9.6: Implementar los siguientes métodos:
 * - getAllUserRankingList(): List<UserRankingEntity>
 * - getUserRankingById(id: Long): UserRankingEntity
 * - deleteUserRanking(entity: UserRankingEntity)
 * - insertUserRankings(entities: List<UserRankingEntity>)
 */
@Dao
interface UserRankingDao{
    @Query("SELECT * FROM userranking")
    fun getAllUserRankingList(): List<UserRankingEntity>

    @Query("SELECT * FROM userranking where user_id == :id")
    fun getUserRankingById(id:Long): UserRankingEntity

    @Delete
    fun deleteUserRanking(rankingEntity: UserRankingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRankings(rankingList: List<UserRankingEntity>)
}