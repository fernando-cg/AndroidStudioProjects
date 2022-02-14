package es.fesac.practica.ui.model.game

class Tile(x: Int, y: Int, var value: Int) : Cell(x, y) {

    constructor(cell: Cell, value: Int) : this(cell.x, cell.y, value)

    /**
     * Es necesario para saber desde qué casilla se originó y evitar que se hagan múltiples
     * unines (comprobando que sea null)
     */
    var mergedFrom: Array<Tile>? = null

    fun updatePosition(cell: Cell) {
        x = cell.x
        y = cell.y
    }
}