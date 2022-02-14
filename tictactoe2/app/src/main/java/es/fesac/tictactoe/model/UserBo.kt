package es.fesac.tictactoe.model

import es.fesac.tictactoe.common.USER_FIELD_EMAIL
import es.fesac.tictactoe.common.USER_FIELD_ID
import es.fesac.tictactoe.common.USER_FIELD_USER

data class UserBo(
    val id: String = "",
    val email: String = "",
    val user: String = ""
)

fun UserBo.toMap() = mapOf<String, Any>(
    USER_FIELD_ID to id,
    USER_FIELD_EMAIL to email,
    USER_FIELD_USER to user
)