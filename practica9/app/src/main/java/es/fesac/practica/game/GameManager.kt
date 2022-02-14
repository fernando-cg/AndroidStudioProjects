package es.fesac.practica.game

import android.util.Log
import es.fesac.practica.ui.extension.isTrue
import es.fesac.practica.ui.model.game.*
import es.fesac.practica.ui.view.BASE_ANIMATION_TIME
import es.fesac.practica.ui.view.BoardView
import kotlin.math.pow

class GameManager(private var boardView: BoardView) {

    private val startingMaxValue = 2048
    //private val startingMaxValue = 16 // Para hacer pruebas de "ganar"
    private var endingMaxValue = 0

    // Definimos los estados del juego
    private var gameState: GameStateEnum = GameStateEnum.NORMAL
    private var lastGameState: GameStateEnum = GameStateEnum.NORMAL
    private var bufferGameState: GameStateEnum = GameStateEnum.NORMAL

    private var numSquaresX = 0
    private var numSquaresY = 0
    var grid: Grid? = null

    private var animationGrid: AnimationGrid? = null

    var canUndo = false

    // Variables para controlar las puntuaciones
    var score: Long = 0
    private var lastScore: Long = 0
    private var bufferScore: Long = 0

    init {
        endingMaxValue = 2.0.pow(boardView.numCellTypes).toInt()
        //endingMaxValue = 8
    }

    fun newGame(numSquaresX: Int, numSquaresY: Int) {
        this.numSquaresX = numSquaresX
        this.numSquaresY = numSquaresY

        // Creamos el grid, en caso de que no sea nulo, limpiamos el tablero
        if (grid == null) {
            grid = Grid(numSquaresX, numSquaresY)
        } else {
            grid?.clearGrid()
        }

        // Creamos el animationGrid, en caso de que no sea nulo, cancelamos todas las animaciones
        if(animationGrid == null){
            animationGrid = AnimationGrid(numSquaresX,numSquaresY)
        } else {
            animationGrid?.cancelAnimations()
        }

        // Inicializamos la puntuación y el estado del juego
        score = 0
        boardView.listener?.updateScore(score)
        gameState = GameStateEnum.NORMAL

        // Inicializar las primeras casillas para empezar el juego
        addStartTiles()

        boardView.resyncTime()

        // Invalidamos el tablero para forzar a que se llame el método "onDraw" y se repinten las vistas
        boardView.invalidate()
    }

    fun move(direction : SwipeDirectionEnum) {
        animationGrid?.cancelAnimations()
        if (isActive().not())
            return

        prepareUndoState()
        val vector = Cell(direction.diffX, direction.diffY)
        val traversalsX = buildTraversalsX(vector)
        val traversalsY = buildTraversalsY(vector)
        var moved = false

        prepareTiles()

        traversalsX.forEach { positionX ->
            traversalsY.forEach { positionY ->
                val cell = Cell(positionX, positionY)
                val tile = grid?.getCellContent(cell)
                tile?.let { tileNotNull ->
                    val positions = findFarthestPosition(cell, vector)
                    val next = grid?.getCellContent(positions[1])
                    if (next != null && next.value == tileNotNull.value && next.mergedFrom == null){
                        val merged = Tile(next, tileNotNull.value * 2)
                        merged.mergedFrom = arrayOf(tileNotNull, next)
                        grid?.insertTile(merged)
                        grid?.removeTile(tileNotNull)
                        tileNotNull.updatePosition(next)
                        score += merged.value
                        animationGrid?.startAnimation(merged.x, merged.y, AnimationTypeEnum.MOVE, BASE_ANIMATION_TIME, 0, intArrayOf(positionX,positionY))
                        animationGrid?.startAnimation(merged.x, merged.y, AnimationTypeEnum.MERGE, BASE_ANIMATION_TIME, BASE_ANIMATION_TIME, null)
                        boardView.listener?.updateScore(score)

                        if (merged.value >= winValue() && gameWon().not()){
                            gameState = when(gameState){
                                GameStateEnum.NORMAL -> GameStateEnum.WIN
                                else -> GameStateEnum.ENDLESS_WIN
                            }

                            animateFinishGame(AnimationTypeEnum.WIN)
                            when (gameState){

                                GameStateEnum.WIN -> {
                                    boardView.listener?.onWin()
                                }
                                else -> {
                                    boardView.listener?.onWinEndlessMode()
                                }
                            }
                        }

                    } else {
                        moveTile(tileNotNull, positions[0])
                        animationGrid?.startAnimation(positions[0].x, positions[0].y, AnimationTypeEnum.MOVE, BASE_ANIMATION_TIME, 0, intArrayOf(positionX, positionY))
                    }
                    if (positionEquals(cell, tileNotNull).not()){
                        moved = true
                    }
                }
            }
        }
        if (moved){
            saveUndoState()
            addRandomTile()
            checkLose()
        }
        boardView.resyncTime()
        boardView.invalidate()
    }

    private fun animateFinishGame(type : AnimationTypeEnum)
    {
        animationGrid?.startFinishingAnimation(type, BASE_ANIMATION_TIME, 0)
    }

    private fun winValue() : Int{
        return when (gameState){
            GameStateEnum.ENDLESS, GameStateEnum.ENDLESS_WIN -> endingMaxValue
            else -> startingMaxValue
        }
    }

    private fun positionEquals(firstCell:Cell, secondCell:Cell ):Boolean{
        return (firstCell.x == secondCell.x && firstCell.y == secondCell.y)
    }

    private fun moveTile(tile: Tile, cell: Cell) {
        grid?.removeTile(tile)
        grid?.field?.get(cell.x)?.set(cell.y, tile)
        tile.updatePosition(cell)
    }

    private fun findFarthestPosition(cell: Cell, vector: Cell): Array<Cell> {
        var previous: Cell
        var nextCell = Cell(cell.x, cell.y)
        do {
            previous = nextCell
            nextCell = Cell(previous.x + vector.x, previous.y + vector.y)
        } while (grid?.isCellWithinBounds(nextCell).isTrue() && grid?.isCellAvailable(nextCell).isTrue())
        return arrayOf(previous, nextCell)
    }

    private fun prepareTiles() {
        grid?.let {
            it.field?.forEach {arrayX ->
                arrayX.forEach { tile ->
                    if (it.isCellOccupied(tile)){
                        tile?.mergedFrom = null
                    }
                }
            }
        }
    }

    fun isAnimationActive(): Boolean{
        return animationGrid?.isAnimationActive().isTrue()
    }

    private fun buildTraversalsX(vector: Cell) : List<Int> {
        val x = mutableListOf<Int>()
        for (positionX in 0 until numSquaresX){
            x.add(positionX)
        }
        if (vector.x == 1) x.reverse()
        return x
    }

    private fun buildTraversalsY(vector: Cell) : List<Int> {
        val y = mutableListOf<Int>()
        for (positionY in 0 until numSquaresY){
            y.add(positionY)
        }
        if (vector.y == 1) y.reverse()
        return y
    }

    /**
     * Tenemos que comprobar que no hayamos perdido ni ganado aún
     */
    fun isActive(): Boolean {
        return (gameLost().not() && gameWon().not())
    }

    private fun gameLost():Boolean{
        return (gameState == GameStateEnum.LOST)
    }

    private fun gameWon():Boolean{
        return (gameState == GameStateEnum.WIN || gameState == GameStateEnum.ENDLESS_WIN)
    }

    private fun checkLose() {
        if (gameWon().not() && moveAvailable().not()){
            gameState = GameStateEnum.LOST
            animateFinishGame(AnimationTypeEnum.LOSS)
            boardView.listener?.onLoss()
        }
    }

    private fun moveAvailable(): Boolean{
        return grid?.isCellsAvailable().isTrue() || tileMatchesAvailable()
    }

    private fun tileMatchesAvailable(): Boolean{
        var result = false

        (0 until numSquaresX).forEach loopX@{ xPosition ->
            (0 until numSquaresY).forEach {yPosition ->
                val tile = grid?.getCellContent(xPosition, yPosition)

                tile?.let {
                    for (direction in SwipeDirectionEnum.values()){
                        val vector = Cell (direction.diffX, direction.diffY)
                        val next = Cell (xPosition + vector.x, yPosition + vector.y)

                        if (grid?.isCellWithinBounds(next).isTrue()){
                            val nextTile = grid?.getCellContent(next)

                            nextTile?.let { if (nextTile.value == tile.value){
                                result = true
                                return@loopX
                            }
                            }
                        }
                    }
                }
            }
        }

        return result
    }
    /**
     * Vuelca la información del tablero actual en el tablero auxiliar y lo mismo para el estado del juego y las puntuaciones
     */
    private fun prepareUndoState(){
        bufferScore = score
        bufferGameState = gameState
        grid?.prepareSaveTiles()
    }

    private fun saveUndoState(){
        lastScore = bufferScore
        lastGameState = bufferGameState
        grid?.saveTiles()
        canUndo = true
    }

    fun revertUndoState(){
        if(canUndo){
            animationGrid?.cancelAnimations()
            grid?.revertTiles()
            score = lastScore
            gameState = lastGameState
            boardView.listener?.updateScore(score)
            boardView.invalidate()
            canUndo = false
        }
    }

    /**
     * Genera el estado inicial del tablero (Sólo dos casillas)
     */
    private fun addStartTiles() {
        val startTiles = 2
        for (randomTile in 0 until startTiles) {
            addRandomTile()
        }
        Log.e("GameManager", "addStartTiles")
    }

    /**
     * Añade una casilla de forma aleatoria al tablero
     */
    private fun addRandomTile() {
        grid?.let {
            if (it.isCellsAvailable()) {
                // Creamos un valor aleatorio donde la probabilidad del 10% equivale al valor 4 y el resto al valor 2
                val valueRandomTile = if (Math.random() < 0.9) {
                    2
                } else {
                    4
                }
                val randomCell = it.randomAvailableCell()
                randomCell?.let { cellNotNull ->
                    val tile = Tile(cellNotNull, valueRandomTile)
                    spawnTile(tile)
                }
            }
        }
    }

    /**
     * Inserta la casilla en el tablero
     */
    private fun spawnTile(tile: Tile) {
        grid?.insertTile(tile)
        animationGrid?.startAnimation(tile.x, tile.y, AnimationTypeEnum.SPAWN, BASE_ANIMATION_TIME, 0, null)
    }

    fun setEndlessMode(){
        gameState = GameStateEnum.ENDLESS
        boardView.invalidate()
    }

    fun getAnimationCellList(positionX: Int, positionY: Int): ArrayList<AnimationCell>{

        return animationGrid?.getAnimationCellList(positionX, positionY) ?: arrayListOf()
    }

    fun tickAll(timeElapsed: Long){
        animationGrid?.tickAll(timeElapsed)
    }


}