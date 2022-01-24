package es.fesac.practica.model

import es.fesac.practica.ui.common.USER_EMAIL
import es.fesac.practica.ui.common.USER_ID
import es.fesac.practica.ui.common.USER_RECORD_MAP
import es.fesac.practica.ui.common.USER_USERNAME

/**
 * TODO 7.3. Implementar el modelo de negocio del usuario:
 * - id. Almacenará el uuid del usuario.
 * - email. Almacenará el email que se utilizó en el registro.
 * - user. Almacenará el usuario que se utilizó en el registro.
 * -recordMap. Almacenará el mapa de id de nivel – puntuación máxima.
 */
data class UserBo(
    val id: String = "",
    val email: String = "",
    val user: String = "",
    val recordMap: Map<String,String> = mapOf()
)

fun UserBo.toMap() = mapOf<String,Any>(
    USER_ID to id,
    USER_EMAIL to email,
    USER_USERNAME to user,
    USER_RECORD_MAP to recordMap
)