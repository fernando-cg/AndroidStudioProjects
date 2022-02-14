package es.fesac.practica.local

import androidx.room.Database
import androidx.room.RoomDatabase
import es.fesac.practica.local.dao.LevelDao
import es.fesac.practica.local.dao.UserRankingDao
import es.fesac.practica.local.entity.LevelEntity
import es.fesac.practica.local.entity.UserRankingEntity

/**
 * TODO 9.1: Implementar la base de datos con las entidades y los daos de Level y UserRanking.
 */
@Database(entities = [LevelEntity::class,UserRankingEntity::class], version = 1)
abstract class MyDatabase:RoomDatabase(){
    abstract fun getLevelDao():LevelDao
    abstract fun getUserRankingDao():UserRankingDao
}