package com.example.tictactoe.model

import com.example.tictactoe.R

enum class BoardItem(val resid: Int?) {
    NONE(null), CROSS(R.drawable.ic_cross), CIRCLE(R.drawable.ic_circle) //esta clase lo que hace es darle valor a estas palabras y se usa para controlar estados o eventos
}