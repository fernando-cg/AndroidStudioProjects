package com.example.tictactoe.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.tictactoe.databinding.CustomeViewBoardViasBinding
import com.example.tictactoe.model.BoardItem
import java.lang.IllegalArgumentException

const val MAX_ITEMS = 9

class BoardView(context: Context,attrs:AttributeSet?) : ConstraintLayout(context,attrs) {
    companion object{//las variables que van aqui son estaticas y las que estan fuera
        const val MAX_ITEMS = 9
    }
    interface BoardClickItemListener{
        fun clickOnItem(imageView: ImageView,position: Int)
    }

    var listener: BoardClickItemListener ?= null

    private var binding : CustomeViewBoardViasBinding? = null //forma de instanciar el binding bien y para que este limpio

    init {//cuando se crea una clase esto es lo que se ejecuta
        binding = CustomeViewBoardViasBinding.inflate(LayoutInflater.from(context),this,true) //inflar la vista desde el contexto de la propia vista proporcionamos el contexto y si esta vinculado al padre en este caso es obligatorio porque tiene otra vista dentro y es un elemento complejo
        setUpClickItems()
    }

    private fun getImgeFromPosition(position: Int):ImageView? = when(position){
            0 -> binding?.customViewImg0
            1 -> binding?.customViewImg1
            2 -> binding?.customViewImg2
            3 -> binding?.customViewImg3
            4 -> binding?.customViewImg4
            5 -> binding?.customViewImg5
            6 -> binding?.customViewImg6
            7 -> binding?.customViewImg7
            8 -> binding?.customViewImg8
            else -> null
    }

    private fun setUpClickItems(){
        for(i in 0 until MAX_ITEMS){
            getImgeFromPosition(i)?.setOnClickListener {
                listener?.clickOnItem(it as ImageView, i)
            }
        }
    }

    fun updateState(listItems:List<BoardItem>){
        if(listItems.size!= MAX_ITEMS) {
            throw IllegalArgumentException("list item size must be equal MAX_ITEMS")
        }

        listItems.forEachIndexed { index, boardItem ->
            boardItem.resid?.let {
                getImgeFromPosition(index)?.setImageResource(it)
            } ?: run{
                getImgeFromPosition(index)?.setImageDrawable(null)
            }
        }
    }

    fun clear(){
        for(i in 0 until MAX_ITEMS){
            getImgeFromPosition(i)?.setImageDrawable(null)
        }
    }

}