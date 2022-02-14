package es.fesac.practica.local.mapper

import es.fesac.practica.local.entity.LevelEntity
import es.fesac.practica.model.LevelBo
import es.fesac.practica.ui.model.LevelVo

fun LevelEntity.toBo() : LevelBo {
    return LevelBo(
        id = id,
        title = title,
        numSquares = numSquares
    )
}

fun LevelBo.toEntity() : LevelEntity {
    return LevelEntity(
        id = id,
        title = title,
        numSquares = numSquares
    )
}