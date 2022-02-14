package es.fesac.practica.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import es.fesac.practica.R
import es.fesac.practica.common.MyApplication
import es.fesac.practica.common.PREFERENCE_KEY_CAN_UNDO
import es.fesac.practica.common.PREFERENCE_KEY_CURRENT_SCORE
import es.fesac.practica.common.PREFERENCE_KEY_UNDO_GRID
import es.fesac.practica.databinding.FragmentGameBinding
import es.fesac.practica.databinding.FragmentLoginBinding
import es.fesac.practica.game.GameManager
import es.fesac.practica.ui.extension.*
import es.fesac.practica.ui.model.game.Tile
import es.fesac.practica.ui.view.BoardView
import es.fesac.practica.ui.viewmodel.GameViewModel

class GameFragment : BaseFragment() {

    private var binding: FragmentGameBinding? = null

    private val viewModel: GameViewModel by viewModels()
    private val args: GameFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLoadLevelHighScoreData().removeObservers(this)
        viewModel.getLoadLevelHighScoreData().observe(this) {
            updateHighScore(it)
        }
        viewModel.getLoadScreenData().removeObservers(this)
        viewModel.getLoadScreenData().observe(this) {
            showLoading(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        setUpViews()
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onPause() {
        binding?.gameCustomViewBoard?.gameManager?.let { gameManagerNotNull ->
            saveGame(gameManagerNotNull)
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding?.gameCustomViewBoard?.gameManager?.let { gameManagerNotNull ->
            loadGame(gameManagerNotNull)
        }
    }

    private fun updateHighScore(highScore: Long?) {
        binding?.gameLabelHighScoreValue?.text = highScore.toString()
    }

    private fun setUpViews() {
        val cellsNumber = args.cellsNumber
        viewModel.loadHighScore(args.idLevel)
        binding?.gameCustomViewBoard?.gameManager?.let { gameManagerNotNull ->
            loadGame(gameManagerNotNull)
        }
        binding?.gameImgNewGame?.setOnClickListener {
            hideGameStatus()
            createNewGame()
        }
        binding?.gameImgUndo?.setOnClickListener {
            undoGame()
        }
        binding?.gameCustomViewBoard?.listener = object : BoardView.OnBoardEvent {
            override fun onWin() {
                showWin()
            }

            override fun onLoss() {
                showLose()
            }

            override fun updateScore(score: Long) {
                viewModel.updateScore(score, args.idLevel)
                binding?.gameLabelScoreValue?.text = score.toString()
            }

            override fun onWinEndlessMode() {
                showWinEndlessMode()
            }
        }

    }

    private fun hideGameStatus() {
        binding?.gameViewGameStatus?.fadeOut()
        binding?.gameLabelGameStatus?.fadeOut()
        binding?.gameLabelGameInstructions?.fadeOut()
    }

    private fun showGameStatus(
        textGameStatus: String,
        textGameInstructions: String,
        showNow: Boolean = true,
        action: () -> Unit
    ) {
        binding?.gameViewGameStatus?.fadeIn()
        binding?.gameLabelGameStatus?.fadeIn()
        binding?.gameLabelGameStatus?.text = textGameStatus
        binding?.gameLabelGameInstructions?.text = textGameInstructions
        binding?.gameLabelGameInstructions?.setOnClickListener { action() }
        if (showNow) {
            binding?.gameLabelGameInstructions?.fadeIn()
        }
    }

    private fun undoGame() {
        binding?.gameCustomViewBoard?.doUndo()
        hideGameStatus()
    }

    private fun createNewGame() {
        binding?.gameCustomViewBoard?.newGame(args.cellsNumber, args.cellsNumber)
        hideGameStatus()
    }

    private fun showWin() {
        showGameStatus(
            getString(R.string.game__congrats),
            getString(R.string.game__congrats_instructions)
        ) {
            binding?.gameCustomViewBoard?.setEndlessMode()
            hideGameStatus()
        }
    }

    private fun showLose() {
        showGameStatus(
            getString(R.string.game__game_over),
            getString(R.string.game__game_over_instructions),
            true
        ) {
            binding?.gameImgNewGame?.postDelayed({
                createNewGame()
            }, 500)
        }
    }

    private fun showWinEndlessMode() {
        showGameStatus(
            getString(R.string.game__congrats),
            getString(R.string.game__congrats_endless_mode_instructions)
        ) {
            createNewGame()
        }
    }

    private fun saveGame(gameManager: GameManager) {
        val test: Int? = null
        test?.toFloat()

        gameManager.grid?.let { gridNotNull ->
            val field = gridNotNull.field
            val undoField = gridNotNull.undoField
            if (field != null && undoField != null) {
                field.forEachIndexed { xPosition, arrayX ->
                    arrayX.forEachIndexed { yPosition, tile ->
                        // Guardado del tablero actual ("3_0 0")
                        MyApplication.instance.saveDataByKeyInt(
                            "${args.cellsNumber}_$xPosition $yPosition",
                            tile?.value
                        )

                        // Guardado del tablero anterior (undo - "KEY_UNDO_GRID_3_0 0")
                        MyApplication.instance.saveDataByKeyInt(
                            "${PREFERENCE_KEY_UNDO_GRID}_${args.cellsNumber}_$xPosition $yPosition",
                            undoField[xPosition][yPosition]?.value
                        )
                    }
                }
            }
            MyApplication.instance.saveDataByKeyLong(PREFERENCE_KEY_CURRENT_SCORE, gameManager.score)
            MyApplication.instance.saveDataByKeyBoolean(PREFERENCE_KEY_CAN_UNDO, gameManager.canUndo)
        }
    }

    private fun loadGame(gameManager: GameManager) {
        binding?.gameCustomViewBoard?.newGame(args.cellsNumber, args.cellsNumber)
        gameManager.grid?.let { gridNotNull ->
            val field = gridNotNull.field
            val undoField = gridNotNull.undoField
            if (field != null && undoField != null) {
                field.forEachIndexed { xPosition, arrayX ->
                    arrayX.forEachIndexed { yPosition, tile ->
                        // Restauramos estado del tablero actual
                        val value = MyApplication.instance.getIntDataByKey(
                            "${args.cellsNumber}_$xPosition $yPosition",
                            -1
                        )
                        if (value > 0) {
                            field[xPosition][yPosition] = Tile(xPosition, yPosition, value)
                        } else if (value == 0) {
                            field[xPosition][yPosition] = null
                        }

                        // Restauramos estado del tablero anterior
                        val undoValue = MyApplication.instance.getIntDataByKey(
                            "${PREFERENCE_KEY_UNDO_GRID}_${args.cellsNumber}_$xPosition $yPosition",
                            -1
                        )
                        if (undoValue > 0) {
                            undoField[xPosition][yPosition] = Tile(xPosition, yPosition, undoValue)
                        } else if (undoValue == 0) {
                            undoField[xPosition][yPosition] = null
                        }
                    }
                }
                binding?.gameLabelScoreValue?.text =
                    "${MyApplication.instance.getLongDataByKey(PREFERENCE_KEY_CURRENT_SCORE)}"
                binding?.gameCustomViewBoard?.gameManager?.canUndo =
                    MyApplication.instance.getBooleanDataByKey(PREFERENCE_KEY_CAN_UNDO)
            }
        }
    }
}