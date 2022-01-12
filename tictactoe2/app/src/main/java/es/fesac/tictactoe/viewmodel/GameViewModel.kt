package es.fesac.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.tictactoe.model.BoardItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel : ViewModel() {

    private val boardStateMutableLiveData by lazy {
        MutableLiveData<List<BoardItem>>()
    }

    private val chanceMutableLiveData by lazy {
        MutableLiveData<BoardItem>()
    }

    private val scoreMutableLiveData by lazy {
        MutableLiveData<String>()
    }

    private val winnerMutableLiveData by lazy {
        MutableLiveData<BoardItem>()
    }

    private val winCombinationMutableLiveData by lazy {
        MutableLiveData<List<Int>>()
    }

    private var boardStateList = mutableListOf(
        BoardItem.NONE, BoardItem.NONE, BoardItem.NONE,
        BoardItem.NONE, BoardItem.NONE, BoardItem.NONE,
        BoardItem.NONE, BoardItem.NONE, BoardItem.NONE
    )
    private val winCombinationsList = listOf<List<Int>>(
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )

    private var chance = BoardItem.CROSS
    private var crossPoints = 0
    private var circlePoints = 0

    fun getBoardStateLiveData(): LiveData<List<BoardItem>> = boardStateMutableLiveData
    fun getChanceLiveData(): LiveData<BoardItem> = chanceMutableLiveData
    fun getScoreLiveData(): LiveData<String> = scoreMutableLiveData
    fun getWinnerLiveData(): LiveData<BoardItem> = winnerMutableLiveData
    fun getWinCombinationLiveData(): LiveData<List<Int>> = winCombinationMutableLiveData

    fun initState() {
        boardStateMutableLiveData.value = boardStateList
        chanceMutableLiveData.value = chance
        scoreMutableLiveData.value = "$crossPoints : $circlePoints"
    }

    fun clickOnItem(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (boardStateList[position] == BoardItem.NONE) {
                boardStateList[position] = chance
                withContext(Dispatchers.Main) {
                    boardStateMutableLiveData.value = boardStateList
                }
                val winnerPair = checkWin()

                if (winnerPair.first != BoardItem.NONE) {
                    withContext(Dispatchers.Main) {
                        winCombinationMutableLiveData.value = winnerPair.second
                    }
                    delay(2000)
                    resetGame()
                    increasePoints(winnerPair.first)
                    nextChance()
                    withContext(Dispatchers.Main) {
                        winnerMutableLiveData.value = winnerPair.first
                        boardStateMutableLiveData.value = boardStateList
                        scoreMutableLiveData.value = "$crossPoints : $circlePoints"
                        chanceMutableLiveData.value = chance
                    }

                } else {
                    nextChance()
                    withContext(Dispatchers.Main) {
                        chanceMutableLiveData.value = chance
                    }
                }
            }
        }
    }

    private fun increasePoints(winner: BoardItem) {
        if (winner == BoardItem.CROSS) {
            crossPoints++
        } else {
            circlePoints++
        }
    }

    private fun resetGame() {
        boardStateList = mutableListOf(
            BoardItem.NONE, BoardItem.NONE, BoardItem.NONE,
            BoardItem.NONE, BoardItem.NONE, BoardItem.NONE,
            BoardItem.NONE, BoardItem.NONE, BoardItem.NONE
        )
    }

    private suspend fun checkWin(): Pair<BoardItem, List<Int>> {
        var winner = BoardItem.NONE
        var winnerCombination = listOf<Int>()

        winCombinationsList.forEach { winCombination ->
            val state = boardStateList[winCombination.first()]
            if (state != BoardItem.NONE) {
                val win = winCombination.all { item ->
                    state == boardStateList[item]
                }
                if (win) {
                    winner = state
                    winnerCombination = winCombination
                    return@forEach
                }
            }
        }

        return Pair(winner, winnerCombination)
    }

    private fun nextChance() {
        chance = when (chance) {
            BoardItem.CROSS -> BoardItem.CIRCLE
            BoardItem.CIRCLE -> BoardItem.CROSS
            else -> BoardItem.CROSS
        }
    }
}