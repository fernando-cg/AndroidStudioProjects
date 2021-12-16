package com.example.tictactoe.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.model.BoardItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel:ViewModel() {
    private val boardStateMutableLiveData by lazy { //cuando se invoca a la variable es cuando se ejecuta lo que vale una variable
        MutableLiveData<List<BoardItem>>()
    }

    private val turnStateMutableLiveData by lazy {
        MutableLiveData<BoardItem>()
    }

    private var boardStateList = mutableListOf(
        BoardItem.NONE,BoardItem.NONE,BoardItem.NONE,
        BoardItem.NONE,BoardItem.NONE,BoardItem.NONE,
        BoardItem.NONE,BoardItem.NONE,BoardItem.NONE
    )

    private val winnerStateMutableLiveData by lazy {
        MutableLiveData<BoardItem>()
    }

    private val winCombinationMutableLiveData by lazy{
        MutableLiveData<List<Int>>()
    }
    private val winCombinations = listOf(
        listOf(0,1,2),
        listOf(3,4,5),
        listOf(6,7,8),
        listOf(0,3,6),
        listOf(1,4,7),
        listOf(2,5,8),
        listOf(0,4,8),
        listOf(2,4,6))

    private val scoreMutableLiveData by lazy {
        MutableLiveData<String>()
    }
    private var chance = BoardItem.CROSS
    private var XPoints = 0
    private var  OPoints = 0

    fun getBoardStateLiveData():LiveData<List<BoardItem>> = boardStateMutableLiveData //asi esta bien mutable live data extiende de live data
    fun getChanceStateLiveData():LiveData<BoardItem> = turnStateMutableLiveData
    fun getScoreLiveData():LiveData<String> = scoreMutableLiveData
    fun getWinnerLiveData():LiveData<BoardItem> = winnerStateMutableLiveData
    fun getWinCombinationLiveData():LiveData<List<Int>> = winCombinationMutableLiveData

    fun initState() {
        boardStateMutableLiveData.value = boardStateList
        turnStateMutableLiveData.value = chance
        scoreMutableLiveData.value = "$XPoints : $OPoints"
    }

    private fun nextChance() {
        chance = when (chance) {
            BoardItem.CROSS -> BoardItem.CIRCLE
            BoardItem.CIRCLE -> BoardItem.CROSS
            else -> BoardItem.CROSS
        }
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
                        winnerStateMutableLiveData.value = winnerPair.first
                        boardStateMutableLiveData.value = boardStateList
                        turnStateMutableLiveData.value = chance
                        scoreMutableLiveData.value = "$XPoints : $OPoints"
                    }

                } else {
                    nextChance()
                    withContext(Dispatchers.Main) {
                        turnStateMutableLiveData.value = chance
                    }
                }
            }
        }
    }

    private fun increasePoints(winner: BoardItem) {
        if(winner==BoardItem.CROSS){
            XPoints++
        }else{
            OPoints++
        }
    }

    private fun resetGame() {
        boardStateList = mutableListOf(
                BoardItem.NONE,BoardItem.NONE,BoardItem.NONE,
                BoardItem.NONE,BoardItem.NONE,BoardItem.NONE,
                BoardItem.NONE,BoardItem.NONE,BoardItem.NONE
        )
    }

    private suspend fun checkWin(): Pair<BoardItem,List<Int>> {
        var winner = BoardItem.NONE
        var winCombinationList = listOf<Int>()
        winCombinations.forEach { winCombination ->
            val state = boardStateList[winCombination.first()]
            if (state != BoardItem.NONE) {
                val win = winCombination.all { item ->
                    state == boardStateList[item]
                }
                if (win) {
                    winner = state
                    winCombinationList = winCombination
                    return@forEach
                }
            }
        }

        return Pair(winner,winCombinationList)
    }
}