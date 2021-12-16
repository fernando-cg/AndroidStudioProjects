package com.example.tictactoe.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.model.BoardItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel:ViewModel() {
    private val boardStateMutableLiveData by lazy { //cuando se invoca a la variable es cuando se ejecuta lo que vale una variable
        MutableLiveData<List<BoardItem>>()
    }

    private val turnStateMutableLiveData by lazy {
        MutableLiveData<BoardItem>()
    }

    private val boardStateList = mutableListOf(
        BoardItem.NONE,BoardItem.NONE,BoardItem.NONE,
        BoardItem.NONE,BoardItem.NONE,BoardItem.NONE,
        BoardItem.NONE,BoardItem.NONE,BoardItem.NONE
    )

    private val winnerStateMutableLiveData by lazy {
        MutableLiveData<BoardItem>()
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
                val winner = checkWin()

                if (winner != BoardItem.NONE) {
                    withContext(Dispatchers.Main) {
                        winnerStateMutableLiveData.value = winner
                    }

                } else {
                    nextChance()
                    withContext(Dispatchers.Main) {
                        winnerStateMutableLiveData.value = chance
                    }
                }
            }
        }
    }

    private suspend fun checkWin(): BoardItem {
        var result = BoardItem.NONE

        winCombinations.forEach { winCombination ->
            val state = boardStateList[winCombination.first()]
            if (state != BoardItem.NONE) {
                val win = winCombination.all { item ->
                    state == boardStateList[item]
                }
                if (win) {
                    result = state
                    return@forEach
                }
            }
        }

        return result
    }
}