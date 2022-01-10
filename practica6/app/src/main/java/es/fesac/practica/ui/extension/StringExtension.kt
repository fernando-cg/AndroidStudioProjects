package es.fesac.practica.ui.extension

import android.util.Patterns

fun String.isEmail() = this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()