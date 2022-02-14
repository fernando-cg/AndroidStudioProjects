package es.fesac.practica.ui.model.game

enum class GameStateEnum(val id: Int) {
    LOST(-1),
    NORMAL(0),
    WIN(1),
    ENDLESS(2),
    ENDLESS_WIN(3)
}