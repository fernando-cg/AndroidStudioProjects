package es.fesac.practica.ui.model

data class UserRankingVo(
    val userId: String = "",
    val userName: String = "",
    val score: Long = 0L,
    val userImgUrl: String = ""
)