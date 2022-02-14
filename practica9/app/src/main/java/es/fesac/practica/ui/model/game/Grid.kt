package es.fesac.practica.ui.model.game

class Grid(sizeX: Int, sizeY: Int) {
    /**
     * Contiene el estado actual del tablero, en él se guardan todas las casillas
     */
    var field: Array<Array<Tile?>>? = null

    /**
     * Contiene el estado previo al actual del tablero (poder hacer el deshacer)
     */
    var undoField: Array<Array<Tile?>>? = null

    /**
     * Se utilza mientras se está realizando un movimiento, de esta forma se puede pasar el
     * estado del tablero actual a él (realizando el movimiento) y luego de éste al estado previo del tablero
     */
    var bufferField: Array<Array<Tile?>>? = null

    init {
        // Inicializamos los arrays y limpiamos los tableros
        field = Array(sizeX) {
            arrayOfNulls(sizeY)
        }
        undoField = Array(sizeX) {
            arrayOfNulls(sizeY)
        }
        bufferField = Array(sizeX) {
            arrayOfNulls(sizeY)
        }
    }

    /**
     * Limpia completamente el tablero actual
     */
    fun clearGrid() {
        field?.forEachIndexed { positionX, arrayX ->
            arrayX.forEachIndexed { positionY, _ ->
                field?.get(positionX)?.set(positionY, null)
            }
        }
    }

    /**
     * Comprueba si existen celdas libres / disponibles
     */
    fun isCellsAvailable(): Boolean {
        return getAvailableCells().isNotEmpty()
    }

    /**
     * Recorre todas las celdas para obtener aquellas que tienen una casilla nula
     */
    private fun getAvailableCells(): List<Cell> {
        val availableCells = mutableListOf<Cell>()
        field?.forEachIndexed { positionX, arrayX ->
            arrayX.forEachIndexed { positionY, tile ->
                if (tile == null) {
                    availableCells.add(Cell(positionX, positionY))
                }
            }
        }
        return availableCells
    }

    /**
     * Obtiene una celda libre al azar.
     */
    fun randomAvailableCell(): Cell? {
        val availableCellList = getAvailableCells()

        return if (availableCellList.isNotEmpty()) {
            availableCellList.random()
        } else {
            null
        }
    }

    /**
     * Inserta una casilla en el tablero
     */
    fun insertTile(tile: Tile){
        field?.get(tile.x)?.set(tile.y, tile)
    }

    /**
     * Obtiene el contenido (casilla) de una celda pasada por parámetros. Puede obtener el contenido de una celda si la celda
     * es distinto de null y si está en dentro del rango de posiciones válidas.
     */
    fun getCellContent(positionX: Int, positionY: Int): Tile? {
        return if (isCellWithinBounds(positionX, positionY)){
            field?.get(positionX)?.get(positionY)
        } else {
            null
        }
    }

    /**
     * Obtiene el contenido (casilla) de una celda pasada por parámetros. Puede obtener el contenido de una celda si la celda
     * es distinto de null y si está en dentro del rango de posiciones válidas.
     */
    fun getCellContent(cell: Cell?): Tile? {
        return if (cell != null && isCellWithinBounds(cell.x, cell.y)){
            field?.get(cell.x)?.get(cell.y)
        } else {
            null
        }
    }

    /**
     * Comprueba si para unas posiciones pasadas por parámetros, éstas están en el rango de posiciones válidas
     */
    fun isCellWithinBounds(xPosition: Int, yPosition: Int): Boolean {
        val maxBoundX = field?.size ?: 0
        val maxBoundY = field?.get(0)?.size ?: 0

        return xPosition in 0 until maxBoundX && yPosition in 0 until maxBoundY
    }

    /**
     * Comprueba si para una celda pasada por parámetros, sus posiciones están en el rango de posiciones válidas
     */
    fun isCellWithinBounds(cell: Cell?): Boolean {
        return cell?.let {cellNotNull ->
            (isCellWithinBounds(cellNotNull.x, cellNotNull.y))
        }?: false
    }

    /**
     * Prepara las casillas para que se puedan posteriormente guardar
     */
    fun prepareSaveTiles() {
        field?.forEachIndexed { positionX, arrayX ->
            arrayX.forEachIndexed { positionY, tile ->
                if (tile == null){
                    bufferField?.get(positionX)?.set(positionY, null)
                } else {
                    val newTile = Tile(positionX, positionY, value = tile.value)
                    bufferField?.get(positionX)?.set(positionY, newTile)
                }
            }
        }
    }

    /**
     * Guarda las casillas para que se pueda realizar la acción de deshacer
     */
    fun saveTiles(){
        bufferField?.forEachIndexed() { positionX, arrayX ->
            arrayX.forEachIndexed { positionY, tile ->
                if (tile == null){
                    undoField?.get(positionX)?.set(positionY, null)
                } else {
                    val newTile = Tile(positionX, positionY, value = tile.value)
                    undoField?.get(positionX)?.set(positionY, newTile)
                }
            }
        }
    }

    /**
     * Comprueba si una celda tiene contenido, si tiene se toma como ocupada.
     */
    fun isCellOccupied(cell: Cell?) : Boolean{
        return getCellContent(cell) != null
    }

    /**
     * Comprueba si una celda pasada por parámetros está libre
     */
    fun isCellAvailable(cell: Cell?) : Boolean{
        return isCellOccupied(cell).not()
    }

    /**
     * Borra una casilla del tablero
     */
    fun removeTile(tile: Tile) {
        field?.get(tile.x)?.set(tile.y, null)
    }

    fun revertTiles(){
        undoField?.forEachIndexed { positionX, arrayX ->
            arrayX.forEachIndexed { positionY, tile ->
                if(tile == null) {
                    field?.get(positionX)?.set(positionY, null)
                } else {
                    field?.get(positionX)?.set(positionY, Tile(positionX,positionY,tile.value))
                }
            }
        }
    }
}