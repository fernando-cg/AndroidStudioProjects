package es.fesac.practica.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO 9.5: Implementar UserRankingEntity, debe tener como nombre de tabla “UserRanking” y los siguientes campos:
 * - userId → Long → “user_id”
 * - userName → String → “user_name”
 * - score → Int
 * - userImgUrl → String → “user_img_url”
 */
@Entity(tableName = "UserRanking")
data class UserRankingEntity(
    @PrimaryKey @ColumnInfo(name="user_id") val userId:Long,
    @ColumnInfo(name="user_name") val userName:String,
    val score:Int,
    @ColumnInfo(name="user_img_url") val userImgUrl:String
)