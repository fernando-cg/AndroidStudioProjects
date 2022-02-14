package es.fesac.tictactoe.model

import es.fesac.tictactoe.R

enum class BoardItem(val resId: Int?) {
    NONE(null), CROSS(R.drawable.ic_cross), CIRCLE(R.drawable.ic_circle)
}