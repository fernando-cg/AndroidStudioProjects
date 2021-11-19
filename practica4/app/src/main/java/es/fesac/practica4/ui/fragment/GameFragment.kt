package es.fesac.practica4.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import es.fesac.practica4.databinding.FragmentGameBinding
import es.fesac.practica4.ui.activity.NavHostActivity

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    // TODO: 4.2 Argumento

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
        // TODO: 4.2 Argumento. Mostrar el nivel pasado en este Toast
        Toast.makeText(activity, "Level ${args.level}", Toast.LENGTH_SHORT).show()

        binding.gameImgNewGame.setOnClickListener {

        }
        binding.gameImgUndo.setOnClickListener {

        }
    }

    private fun getNavController() = if (activity is NavHostActivity) {
        (activity as NavHostActivity).getNavController()
    } else {
        null
    }
}