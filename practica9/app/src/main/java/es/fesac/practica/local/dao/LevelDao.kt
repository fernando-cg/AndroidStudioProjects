package es.fesac.practica.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import es.fesac.practica.local.entity.LevelEntity

/**
 * TODO 9.3: Implementar los siguientes métodos:
 * - getAllLevelList(): List<LevelEntity>
 * - getLevelById(id: Long): LevelEntity
 * - deleteLevel(entity: LevelEntity)
 * - insertLevels(entities: List<LevelEntity>)
 */
@Dao
interface LevelDao{
    @Query("SELECT * FROM Level")
    fun getAllLevelList(): List<LevelEntity>

    @Query("SELECT * FROM Level where id == :id")
    fun getLevelById(id:Long): LevelEntity

    @Delete
    fun deleteLevel(levelEntity: LevelEntity)

    @Insert(onConflict = REPLACE)
    fun insertLevels(levelList: List<LevelEntity>)
}