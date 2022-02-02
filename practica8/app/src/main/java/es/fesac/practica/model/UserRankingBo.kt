package es.fesac.practica.model

/**
 * TODO 8.3 UserRankingBo
 * Implementar el modelo de negocio del usuario de ranking:
 * - userId. Almacenará el id del usuario.
 * - userName. Almacenará el usuario que se utilizó en el registro.
 * - score. Almacenará la puntuación.
 * - userImgUrl. Almacenará la url del avatar del usuario.
 */
data class UserRankingBo(
    val userId:String="",
    val userName:String="",
    val score:Long=0,
    val userImgUrl:String=""
    )