package es.fesac.practica.ui.mapper

import es.fesac.practica.model.UserRankingBo
import es.fesac.practica.ui.model.UserRankingVo

fun UserRankingBo.toVo() : UserRankingVo {
    return UserRankingVo(
        userId = userId,
        userName = userName,
        score = score,
        userImgUrl = userImgUrl
    )
}