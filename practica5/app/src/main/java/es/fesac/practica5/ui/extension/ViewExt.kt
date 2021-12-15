package es.fesac.practica5.ui.extension

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.visible(){
    this.visibility = VISIBLE
}

fun View.invisible(){
    this.visibility = GONE
}