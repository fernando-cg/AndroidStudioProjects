package es.fesac.practica.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import es.fesac.practica.R
import es.fesac.practica.game.GameManager
import es.fesac.practica.ui.model.game.AnimationTypeEnum
import es.fesac.practica.ui.model.game.SwipeDirectionEnum
import es.formacion.game2048.ui.view.OnSwipeTouchListener
import kotlin.math.log2
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

const val BASE_ANIMATION_TIME = 100L

class BoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    interface OnBoardEvent {
        fun onWin()
        fun onLoss()
        fun updateScore(score: Long)
        fun onWinEndlessMode()
    }

    var listener: OnBoardEvent? = null

    // Objeto donde se pintarán todas las configuraciones / estilos de vistas
    private val paint = Paint()

    // Coordenadas de nuestro tablero
    var startingX = 0
    var startingY = 0
    var endingX = 0
    var endingY = 0

    // Tamaños de las vistas en función de la pantalla
    private var cellSize = 0
    private var textSize = 0f
    // Tamaño del separador de las celdas
    private var gridWidth = 0

    // Background del tablero
    private var backgroundRectangle: Drawable? = null
    private var backgroundBitmap: Bitmap? = null

    // Número de elementos (x e y)
    var numSquaresX = 3
    var numSquaresY = 3

    // Número máximo de casilals que vamso a soportar
    val numCellTypes = 21 // 2, 4, 8, 16, 32, ..... 2097152

    private val bitmapCell = arrayOfNulls<BitmapDrawable>(numCellTypes)

    var gameManager: GameManager? = null

    private var lastTimeAnimationPrint = System.currentTimeMillis()
    private var mergingAcceleration = -0.2F

    init {
        // Inicializar el manager del juego
        gameManager = GameManager(this)

        backgroundRectangle = ResourcesCompat.getDrawable(resources, R.drawable.bg_board, null)
        // Suaviza los bordes de lo que se está dibujando, pero no tiene impacto en el interior de la forma
        paint.isAntiAlias = true
        // Indicamos que la vista es clickable y por tanto puede capturar gestos (swipe)
        isClickable = true

        setOnTouchListener(object : OnSwipeTouchListener(context){
            override fun onSwipeBottom() {
                gameManager?.move(SwipeDirectionEnum.DOWN)
            }

            override fun onSwipeTop() {
                gameManager?.move(SwipeDirectionEnum.UP)
            }

            override fun onSwipeLeft() {
                gameManager?.move(SwipeDirectionEnum.LEFT)
            }

            override fun onSwipeRight() {
                gameManager?.move(SwipeDirectionEnum.RIGHT)
            }
        })
    }

    /**
     * Se llama para determinar los requisitos de tamaño para esta vista y todos sus elementos hijos.
     * Es de los primeros métodos en ejecutarse
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val containerWidth = MeasureSpec.getSize(widthMeasureSpec)
        val containerHeight = MeasureSpec.getSize(heightMeasureSpec)
        calculateDimensions(containerWidth, containerHeight)
        setMeasuredDimension(endingX - startingX, endingY - startingY)
        Log.e("BoardView", "onMeasure: $widthMeasureSpec - $heightMeasureSpec")
    }

    /**
     * Se llama cuando el tamaño de esta vista ha cambiado.
     * Una vez que se sepa el tamaño (ha cambiado respecto al original), se invocará automáticamente.
     */
    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        Log.e("BoardView", "onSizeChanged")
        super.onSizeChanged(width, height, oldw, oldh)
        calculateTextSize()
        createBitmapCell()
        createBackgroundBitmap(width, height)
        resyncTime()
    }

    /**
     * Se llama cuando la vista debe representar su contenido. Forzaremos que se llame invocando
     * al método invalidate().
     * Canvas -> Contendrá todos los elementos dibujables de nuestra vista. Para ello necesita siempre
     * 4 componentes:
     * - Un Bitmap dónde estarán los píxeles a pintar.
     * - Los objetos a pintar.
     * - Un objeto pintable primitivo (Rect, Path, etc)
     * - Un Paint que describirá los colores y estilos para el pintado
     */
    override fun onDraw(canvas: Canvas?) {
        Log.e("BoardView", "onDraw")
        super.onDraw(canvas)
        canvas?.let { canvasNotNull ->
            backgroundBitmap?.let {
                canvasNotNull.drawBitmap(it, 0F, 0F, paint)
                drawCells(canvasNotNull)
            }
            gameManager?.let { gameManagerNotNull ->
                if (gameManagerNotNull.isAnimationActive()){
                    invalidate()
                    tick()
                } else if(gameManagerNotNull.isActive().not()){
                    invalidate()
                }
            }
        }
    }

    /**
     * Dibujamos el texto centrado dado un canvas
     */
    private fun drawCells(canvas: Canvas) {
        // Recorremos todas las celdas a dibujar
        for (xPosition in 0 until numSquaresX){
            for (yPosition in 0 until numSquaresY){
                val startCellX: Int = startingX + gridWidth + (cellSize + gridWidth) * xPosition
                val endCellX: Int = startCellX + cellSize
                val startCellY: Int = startingY + gridWidth + (cellSize + gridWidth) * yPosition
                val endCellY: Int = startCellY + cellSize

                // Sacamos la casilla para ver si tiene o no valor
                val currentTile = gameManager?.grid?.getCellContent(xPosition, yPosition)
                currentTile?.let { tileNotNull ->
                    // El índice del bitmap es la potencia a la que está elevada la casilla
                    val index = log2(tileNotNull.value.toDouble()).toInt()

                    val animationArray = gameManager?.getAnimationCellList(xPosition, yPosition)
                    var animated = false
                    animationArray?.forEach{ animationCell ->
                        if (animationCell.isActive()){
                            when(animationCell.animationType){
                                AnimationTypeEnum.SPAWN -> {
                                    val percentDone = animationCell.getPercentageDone()
                                    Log.e("boardView", "percentSpawnDone: $percentDone")
                                    paint.textSize = textSize * percentDone
                                    val cellScaleSize = cellSize / 2 * (1 - percentDone)
                                    bitmapCell[index]?.setBounds(
                                        (startCellX + cellScaleSize).toInt(),
                                        (startCellY + cellScaleSize).toInt(),
                                        (endCellX - cellScaleSize).toInt(),
                                        (endCellY - cellScaleSize).toInt()
                                    )
                                    bitmapCell[index]?.draw(canvas)
                                }
                                AnimationTypeEnum.MERGE -> {
                                    val percentDone = animationCell.getPercentageDone()
                                    paint.textSize = textSize * percentDone * mergingAcceleration

                                    val cellScaleSize = (cellSize / 2 * (1 - percentDone)) * mergingAcceleration

                                    bitmapCell[index]?.setBounds(
                                        (startCellX + cellScaleSize).toInt(),
                                        (startCellY + cellScaleSize).toInt(),
                                        (endCellX - cellScaleSize).toInt(),
                                        (endCellY - cellScaleSize).toInt()
                                    )
                                    bitmapCell[index]?.draw(canvas)
                                }
                                AnimationTypeEnum.MOVE -> {
                                    val percentDone = animationCell.getPercentageDone()
                                    var lastIndex = index
                                    if (animationArray.size >= 2)
                                    {
                                        lastIndex--
                                    }
                                    val originX = animationCell.extras?.get(0) ?: 0
                                    val originY = animationCell.extras?.get(1) ?: 0
                                    val goalX = animationCell.x
                                    val goalY = animationCell.y

                                    val distanceX = (cellSize + gridWidth) * (goalX - originX)
                                    val distanceY = (cellSize + gridWidth) * (goalY - originY)

                                    val remainingX = distanceX * (percentDone - 1)
                                    val remainingY = distanceY * (percentDone - 1)

                                    bitmapCell[lastIndex]?.setBounds(
                                        ((startCellX + remainingX).toInt()),
                                        ((startCellY + remainingY).toInt()),
                                        ((endCellX + remainingX).toInt()),
                                        ((endCellY + remainingY).toInt())
                                    )
                                    bitmapCell[lastIndex]?.draw(canvas)
                                }
                                else -> {
                                    val percentDone = animationCell.getPercentageDone()
                                    paint.textSize = textSize * percentDone * mergingAcceleration

                                    val cellScaleSize = (cellSize / 2 * (1 - percentDone)) * mergingAcceleration

                                    bitmapCell[index]?.setBounds(
                                        (startCellX + cellScaleSize).toInt(),
                                        (startCellY + cellScaleSize).toInt(),
                                        (endCellX - cellScaleSize).toInt(),
                                        (endCellY - cellScaleSize).toInt()
                                    )
                                    bitmapCell[index]?.draw(canvas)

                                    val drawableCellGameState = if (animationCell.animationType == AnimationTypeEnum.WIN)
                                    {
                                        drawGameStateCell(R.drawable.cell_win, percentDone)
                                    }
                                    else {
                                        drawGameStateCell(R.drawable.cell_lose, percentDone)
                                    }

                                    drawableCellGameState?.let {
                                        drawDrawable(canvas,drawableCellGameState,
                                            (startCellX + cellScaleSize).toInt(),
                                            (startCellY + cellScaleSize).toInt(),
                                            (endCellX - cellScaleSize).toInt(),
                                            (endCellY - cellScaleSize).toInt())
                                    }
                                }
                            }

                            animated = true
                        }

                    }

                    if (animated.not()){
                        bitmapCell[index]?.setBounds(startCellX,startCellY,endCellX,endCellY)
                        bitmapCell[index]?.draw(canvas)
                    }

                }
            }
        }
    }

    private fun drawGameStateCell(id : Int, percent : Float) : Drawable? {
        return ResourcesCompat.getDrawable(resources,id,null)
    }

    /**
     * Va a devolver un INT ARRAY con todos los ID de los fondos de nuestras celdas
     */
    private fun getCellRectangleIds() : IntArray {
        val cellRectangleIds = IntArray(numCellTypes)
        cellRectangleIds[0] = R.drawable.cell_rectangle_2
        cellRectangleIds[1] = R.drawable.cell_rectangle_2
        cellRectangleIds[2] = R.drawable.cell_rectangle_4
        cellRectangleIds[3] = R.drawable.cell_rectangle_8
        cellRectangleIds[4] = R.drawable.cell_rectangle_16
        cellRectangleIds[5] = R.drawable.cell_rectangle_32
        cellRectangleIds[6] = R.drawable.cell_rectangle_64
        cellRectangleIds[7] = R.drawable.cell_rectangle_128
        cellRectangleIds[8] = R.drawable.cell_rectangle_256
        cellRectangleIds[9] = R.drawable.cell_rectangle_512
        cellRectangleIds[10] = R.drawable.cell_rectangle_1024
        cellRectangleIds[11] = R.drawable.cell_rectangle_2048

        for (position in 12 until cellRectangleIds.size){
            cellRectangleIds[position] = R.drawable.cell_rectangle_4096
        }
        return cellRectangleIds
    }


    /**
     * Crea los bitmaps de las casillas y las almacenará en el array celdas
     */
    private fun  createBitmapCell() {
        val cellRectangleIds = getCellRectangleIds()

        paint.textAlign = Paint.Align.CENTER
        for (item in 1 until bitmapCell.size) {
            // Elevar 2 al índice iterado
            val value = 2.0.pow(item.toDouble()).toInt()

            paint.textSize = textSize
            val tempTextSize = (textSize * cellSize * 0.9f) / max(cellSize * 0.9f, paint.measureText(value.toString()))
            paint.textSize = tempTextSize

            // Creamos el bitmap donde vamos a pintar el fondo
            val bitmap = Bitmap.createBitmap(cellSize, cellSize, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val cellBackground = ResourcesCompat.getDrawable(resources, cellRectangleIds[item], null)
            cellBackground?.let {
                drawDrawable(canvas, it, 0, 0, cellSize, cellSize)
            }

            // Dibujamos el texto
            drawCellText(canvas, value)

            // Alamacenar en el array de bitmaps para reutilizarse posteriormente
            bitmapCell[item] = BitmapDrawable(resources, bitmap)
        }
    }

    /**
     * Dibujamos el texto centrado dado un canvas
     */
    private fun drawCellText(canvas: Canvas, value: Int) {
        // Calculamos el desplazamiento a partir del cuál pintaremos el texto respecto del eje y
        val textShifty: Int = centerText()

        val colorID = if (value >= 8){
            R.color.text_white
        }
        else{
            R.color.text_black
        }

        paint.color = ResourcesCompat.getColor(resources, colorID, null)
        canvas.drawText("$value", (cellSize / 2).toFloat(), (cellSize / 2).toFloat() - textShifty, paint)
    }

    /**
     * Calcula el punto central para centrar el texto con respecto a su baseline (Eje Y)
     * @return
     */
    private fun centerText(): Int {
        return ((paint.descent() + paint.ascent()) / 2).toInt()
    }

    /**
     * Creamos un tablero nuevo
     */
    fun newGame(numSquaresX: Int, numSquaresY: Int) {
        this.numSquaresX = numSquaresX
        this.numSquaresY = numSquaresY
        gameManager?.newGame(numSquaresX, numSquaresY)
    }

    /**
     * Crear tanto el fondo del tablero como el grid del mismo
     */
    private fun createBackgroundBitmap(width: Int, height: Int) {
        backgroundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        backgroundBitmap?.let {
            val canvas = Canvas(it)
            drawBackground(canvas)
            drawBackgroundGrid(canvas)
        }
    }

    /**
     * Pinta el grid del tablero (de fondo)
     * @param canvas
     */
    private fun drawBackgroundGrid(canvas: Canvas) {
        val backgroundCell = ResourcesCompat.getDrawable(resources, R.drawable.cell_rectangle, null)
        // Recorrer todas las celdas a dibujar
        for (xPosition in 0 until  numSquaresX) {
            for (yPosition in 0 until numSquaresY) {
                val startCellX: Int = startingX + gridWidth + (cellSize + gridWidth) * xPosition
                val endCellX: Int = startCellX + cellSize
                val startCellY: Int = startingY + gridWidth + (cellSize + gridWidth) * yPosition
                val endCellY: Int = startCellY + cellSize
                backgroundCell?.let {
                    drawDrawable(canvas, it, startCellX, startCellY, endCellX, endCellY)
                }
            }
        }
    }

    /**
     * Pintar el fondo del tablero
     */
    private fun drawBackground(canvas: Canvas) {
        backgroundRectangle?.let {
            drawDrawable(canvas, it, startingX, startingY, endingX, endingY)
        }
    }

    /**
     * Pinta en el canvas el objeto (drawable) que se necesita
     */
    private fun drawDrawable(
        canvas: Canvas,
        drawable: Drawable,
        startingX: Int,
        startingY: Int,
        endingX: Int,
        endingY: Int
    ) {
        drawable.setBounds(startingX, startingY, endingX, endingY)
        drawable.draw(canvas)
    }

    /**
     * Calcular los tamaños de los textos a mostrar con respecto al tamaño de la casilla
     */
    private fun calculateTextSize() {
        paint.textSize = cellSize.toFloat()
        textSize = cellSize * cellSize / max(cellSize.toFloat(), paint.measureText("0000"))
    }

    /**
     * Calcula las dimensiones del tablero en función del número de celdas
     */
    private fun calculateDimensions(width: Int, height: Int) {
        val margin = 32
        val widthWithPadding = width - margin
        val heightWithPadding = height - margin
        cellSize = min(widthWithPadding / (numSquaresX + 1), heightWithPadding / (numSquaresY + 1))
        gridWidth = cellSize / 7

        // Establecemos el tamaño de nuestro tablero en función del tamaño de la pantalla
        startingX = 0
        endingX =  gridWidth + ((cellSize + gridWidth) * numSquaresX)
        startingY = 0
        endingY = gridWidth + ((cellSize + gridWidth) * numSquaresX)
    }

    fun setEndlessMode() {
        gameManager?.setEndlessMode()
    }

    fun doUndo() {
        gameManager?.revertUndoState()
    }

    private fun tick() {
        val currentTime = System.currentTimeMillis()
        gameManager?.tickAll(currentTime - lastTimeAnimationPrint)
        lastTimeAnimationPrint = currentTime
    }

    fun resyncTime() {
        lastTimeAnimationPrint = System.currentTimeMillis()
    }
}