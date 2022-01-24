package es.fesac.tictactoe.model

import android.provider.ContactsContract

data class UserBo(val id:String,val email: String,val user:String)

fun UserBo.toMap() = mapOf<String,Any>(
    "id" to id,
    "email" to email,
    "user" to user
)