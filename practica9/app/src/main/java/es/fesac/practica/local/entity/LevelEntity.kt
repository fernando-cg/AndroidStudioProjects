package es.fesac.practica.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO 9.2: Implementar LevelEntity, debe tener como nombre de tabla “Level” y los siguientes campos:
 * - id → Long
 * - title → String
 * - numSquares → Int → num_squares
 */

@Entity(tableName = "Level")
data class LevelEntity(
    @PrimaryKey val id:Long,
    val title:String,
    @ColumnInfo(name="num_squares") val numSquares:Int
)