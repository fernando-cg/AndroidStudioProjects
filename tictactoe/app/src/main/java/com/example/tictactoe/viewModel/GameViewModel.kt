package com.example.tictactoe.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.model.BoardItem

class GameViewModel:ViewModel() {
    private val boardStateMutableLiveData by lazy { //cuando se invoca a la variable es cuando se ejecuta lo que vale una variable
        MutableLiveData<List<BoardItem>>()
    }

    private val boardStateList = mutableListOf(
        BoardItem.NONE,BoardItem.NONE,BoardItem.NONE,
        BoardItem.NONE,BoardItem.NONE,BoardItem.NONE,
        BoardItem.NONE,BoardItem.NONE,BoardItem.NONE
    )

    private var chance = BoardItem.CROSS

    fun getBoardStateLiveData():LiveData<List<BoardItem>> = boardStateMutableLiveData //asi esta bien mutable live data extiende de live data

    private fun nextChance(){
        chance = when(chance){
            BoardItem.CROSS-> BoardItem.CIRCLE
            BoardItem.CIRCLE-> BoardItem.CROSS
            else -> BoardItem.CIRCLE
        }
    }
    fun clickOnItem(position: Int) {
        boardStateList[position]=chance
        nextChance()
        boardStateMutableLiveData.value = boardStateList
    }
}