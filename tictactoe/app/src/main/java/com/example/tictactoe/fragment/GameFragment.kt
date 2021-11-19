package com.example.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tictactoe.databinding.FragmentGameBinding

class GameFragment:Fragment() {

    val args: GameFragmentArgs by navArgs()
    private lateinit var binding:FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater)
        Toast.makeText(context,"Type: ${args.gameType}",Toast.LENGTH_SHORT).show()
        return binding.root
    }

}