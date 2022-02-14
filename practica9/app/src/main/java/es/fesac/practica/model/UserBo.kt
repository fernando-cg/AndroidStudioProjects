package es.fesac.practica.model

import es.fesac.practica.common.USER_EMAIL
import es.fesac.practica.common.USER_ID
import es.fesac.practica.common.USER_RECORD_MAP
import es.fesac.practica.common.USER_USERNAME

data class UserBo(
    var id: String = "",
    val email: String = "",
    val user: String = "",
    val recordMap: MutableMap<String, Long> = hashMapOf()
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            USER_EMAIL to email,
            USER_ID to id,
            USER_USERNAME to user,
            USER_RECORD_MAP to recordMap
        )
    }
}