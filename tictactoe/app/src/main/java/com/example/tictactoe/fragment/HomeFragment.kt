package com.example.tictactoe.fragment

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import com.example.tictactoe.NavHostActivity
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        binding.mainBtnMultiplayer.setOnClickListener {
            getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
        }
    }

    private fun getNavController() = (activity as? NavHostActivity)?.getNavController()


}