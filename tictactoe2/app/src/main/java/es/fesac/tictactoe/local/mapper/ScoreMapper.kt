package es.fesac.tictactoe.local.mapper

import es.fesac.tictactoe.local.entity.ScoreEntity
import es.fesac.tictactoe.model.ScoreBo

fun ScoreBo.toEntity(): ScoreEntity {
    return ScoreEntity(
        score = score,
        date = date
    )
}

fun ScoreEntity.toBo(): ScoreBo {
    return ScoreBo(
        score = score,
        date = date
    )
}