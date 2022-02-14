package es.fesac.practica.model

data class UserRankingBo(
    val userId: String = "",
    val userName: String = "",
    val score: Long = 0L,
    val userImgUrl: String = ""
)