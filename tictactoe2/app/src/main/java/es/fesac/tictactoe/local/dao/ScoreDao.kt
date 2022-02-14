package es.fesac.tictactoe.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import es.fesac.tictactoe.local.entity.ScoreEntity

@Dao
interface ScoreDao {
    @Query("SELECT * FROM Score")
    fun getAllScoreList(): List<ScoreEntity>

    @Query("SELECT * FROM Score WHERE id == :id")
    fun getScoreEntityById(id: Long): ScoreEntity

    @Query("SELECT score FROM Score WHERE id == :id")
    fun getScoreValueById(id: Long): String

    @Delete
    fun deleteScore(scoreEntity: ScoreEntity)

    @Insert(onConflict = REPLACE)
    fun insertScores(scoreList: List<ScoreEntity>)
}