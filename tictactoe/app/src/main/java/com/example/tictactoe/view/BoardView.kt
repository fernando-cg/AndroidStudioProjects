package com.example.tictactoe.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tictactoe.databinding.CustomeViewBoardViasBinding

class BoardView(context: Context,attrs:AttributeSet?) : ConstraintLayout(context,attrs) {
    private var binding : CustomeViewBoardViasBinding? = null //forma de instanciar el binding bien y para que este limpio

    init {//cuando se crea una clase esto es lo que se ejecuta
        binding = CustomeViewBoardViasBinding.inflate(LayoutInflater.from(context),this,true) //inflar la vista desde el contexto de la propia vista proporcionamos el contexto y si esta vinculado al padre en este caso es obligatorio porque tiene otra vista dentro y es un elemento complejo
    }
}