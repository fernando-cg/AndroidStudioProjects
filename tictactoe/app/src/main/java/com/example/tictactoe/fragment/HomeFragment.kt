package com.example.tictactoe.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tictactoe.NavHostActivity
import com.example.tictactoe.databinding.FragmentHomeBinding
import com.example.tictactoe.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private val viewModel:HomeViewModel by viewModels() //creo el viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.multiplayerLiveData.removeObservers(this) //Con elimino todas las subscripciones que tenga de ese veneto en el fragment
        viewModel.multiplayerLiveData.observe(this,{ multiplayerState ->
            val multiplayerVisibility = if(multiplayerState){
                View.VISIBLE
            }else{
                View.GONE
            }
            binding.mainBtnMultiplayer.visibility = multiplayerVisibility
        })//Con esto me subscribo al el live data y le creo una lambda en la cual va a decir que es lo que hay que enviar y con esto recibe datos del viewmodel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        setUpViews()
        initViews()
        return binding.root
    }

    private fun initViews() {
        viewModel.getMultiplayerState() //Le pedimos el estado al viewModel
    }

    private fun setUpViews() {
        binding.mainBtnMultiplayer.setOnClickListener {
            getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment("multi"))
        }
        binding.mainBtnSinglePlayer.setOnClickListener{
            getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment("single"))
        }
    }

    private fun getNavController() = (activity as? NavHostActivity)?.getNavController()


}