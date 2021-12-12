package es.fesac.practica5.ui.extension

import android.util.Patterns

fun String.isEmail() = this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()