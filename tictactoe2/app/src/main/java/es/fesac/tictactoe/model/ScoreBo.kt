package es.fesac.tictactoe.model

data class ScoreBo(val score: String = "", override var date: Long = 0L) : DatableItem(date)