package es.fesac.practica.ui.model.game

enum class SwipeDirectionEnum(val diffX: Int, val diffY: Int) {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0)
}