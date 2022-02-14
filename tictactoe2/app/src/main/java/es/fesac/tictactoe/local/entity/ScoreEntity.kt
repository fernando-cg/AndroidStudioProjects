package es.fesac.tictactoe.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Score")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val score: String,
    @ColumnInfo(name = "timestamp") val date: Long
)