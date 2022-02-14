package es.fesac.tictactoe.local

import androidx.room.Database
import androidx.room.RoomDatabase
import es.fesac.tictactoe.local.dao.ScoreDao
import es.fesac.tictactoe.local.entity.ScoreEntity

@Database(entities = [ScoreEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getScoreDao(): ScoreDao
}