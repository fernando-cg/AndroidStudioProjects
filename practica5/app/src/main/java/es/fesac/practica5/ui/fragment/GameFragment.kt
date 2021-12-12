package es.fesac.practica5.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import es.fesac.practica5.databinding.FragmentGameBinding
import es.fesac.practica5.ui.activity.NavHostActivity

class GameFragment : BaseFragment() {

    private lateinit var binding: FragmentGameBinding

    private val args: GameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        Toast.makeText(activity, "Level ${args.cellsNumber}", Toast.LENGTH_SHORT).show()

        binding.gameImgNewGame.setOnClickListener {

        }
        binding.gameImgUndo.setOnClickListener {

        }
    }
}