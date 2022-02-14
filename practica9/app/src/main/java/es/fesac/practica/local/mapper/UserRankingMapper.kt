package es.fesac.practica.local.mapper

import es.fesac.practica.local.entity.UserRankingEntity
import es.fesac.practica.model.UserRankingBo
import es.fesac.practica.ui.model.UserRankingVo

fun UserRankingEntity.toBo() : UserRankingBo {
    return UserRankingBo(
        userId = userId.toString(),
        userName = userName,
        score = score,
        userImgUrl = userImgUrl
    )
}
//Preguntar el Long por Int
fun UserRankingBo.toEntity() : UserRankingEntity {
    return UserRankingEntity(
        userId = userId.toLong(),
        userName = userName,
        score = score,
        userImgUrl = userImgUrl
    )
}