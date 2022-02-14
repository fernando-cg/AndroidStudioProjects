package es.fesac.practica.ui.mapper

import es.fesac.practica.model.LevelBo
import es.fesac.practica.ui.model.LevelVo

fun LevelBo.toVo() : LevelVo {
    return LevelVo(
        id = id,
        title = title,
        cellsNumber = numSquares,
        record = 0
    )
}