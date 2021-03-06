package com.example.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.extension.showToast
import com.example.tictactoe.model.BoardItem
import com.example.tictactoe.view.BoardView
import com.example.tictactoe.viewModel.GameViewModel

class GameFragment:Fragment() {

    val args: GameFragmentArgs by navArgs()
    private lateinit var binding:FragmentGameBinding

    private val viewmodel:GameViewModel by viewModels() //instanciamos el viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeBoardState()
        observeChanceState()
        observeScoreStatus()
        observeWinnerState()
        observeWinCombinationState()
        viewmodel.initState()
    }

    private fun observeWinCombinationState() {
        viewmodel.getWinCombinationLiveData().removeObservers(this)
        viewmodel.getWinCombinationLiveData().observe(this,{ winCombination ->
            binding.gameViewBoard.updateWinCombination(winCombination)
            binding.gameViewBoard.isEnabled = false
        })
    }

    private fun observeWinnerState() {
        viewmodel.getWinnerLiveData().removeObservers(this)
        viewmodel.getWinnerLiveData().observe(this,{
            binding.gameViewBoard.clear()
            binding.gameViewBoard.isEnabled = true
        })
    }

    private fun observeScoreStatus() {
        viewmodel.getScoreLiveData().removeObservers(this)
        viewmodel.getScoreLiveData().observe(this,{ score ->
            setScore(score)
        })
    }

    private fun setScore(score: String?) {
        binding.gameLavelPoints.text = score
    }

    private fun observeChanceState() {
        viewmodel.getChanceStateLiveData().removeObservers(this)
        viewmodel.getChanceStateLiveData().observe(this,{ chance ->
            setChance(chance)
        })
    }

    private fun setChance(chance: BoardItem) {
        when(chance){
             BoardItem.CROSS ->{
                 binding.gameViewCircleChance.visibility = View.GONE
                 binding.gameViewCrossChance.visibility = View.VISIBLE

             } else ->{
                binding.gameViewCrossChance.visibility = View.GONE
                binding.gameViewCircleChance.visibility = View.VISIBLE
            }
        }
    }

    private fun observeBoardState(){
        viewmodel.getBoardStateLiveData().removeObservers(this)
        viewmodel.getBoardStateLiveData().observe(this,{
            binding.gameViewBoard.updateState(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater)
        setUpViews()
        return binding.root
    }
    //en kotlin las lambdas que vayan al final se pueden separar de los parentesis(){
    // }
    // action:()-> Unit {} esto es una lambda que no recibe ni retorna nada es una funcion que se pasa a otra funcion y las funciones que reciven funciones se llaman funciones de orden superior high-order function una funcion puede redibir una funcion
    private fun setUpViews() {
        binding.gameViewBoard.listener = object : BoardView.BoardClickItemListener{ //asi estoy implementando una interfaz en esta parte de la clase
            override fun clickOnItem(imageView: ImageView, position: Int) {
                viewmodel.clickOnItem(position)
            }

        }
    }

}