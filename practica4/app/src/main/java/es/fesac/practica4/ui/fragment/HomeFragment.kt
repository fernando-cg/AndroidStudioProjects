package es.fesac.practica4.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import es.fesac.practica4.R
import es.fesac.practica4.databinding.FragmentHomeBinding
import es.fesac.practica4.ui.activity.NavHostActivity
import es.fesac.practica4.ui.common.MAX_SQUARES
import es.fesac.practica4.ui.common.MIN_SQUARES
import es.fesac.practica4.ui.model.LevelVo
import es.fesac.practica4.ui.viewmodel.HomeViewModel
import es.formacion.game2048.extension.hide
import es.formacion.game2048.extension.show

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var levelList = listOf<LevelVo>()
    private var position = 0

    // TODO: 4.3 ViewModel
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 4.3 ViewModel

        viewModel.loadLevelsLiveData.removeObservers(this)
        viewModel.loadLevelsLiveData.observe(this,{ listLevel ->
            levelList = listLevel
            setInfo() //he puesto esto aqui par que cuando inicie el programa no se quede en blanco el hueco de los niveles
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpViews()
        viewModel.loadLevels()
        return binding.root
    }

    private fun setUpViews() {
        binding.homeImgLogout.setOnClickListener {
            getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }
        binding.homeBtnGo.setOnClickListener {
            // TODO: 4.2 Argumento
            getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment(levelList[position].cellsNumber.toString()))
        }
        binding.homeImgBack.setOnClickListener {
            position--
            if (position < 0) {
                position = 0
            }
            setInfo()
        }
        binding.homeImgNext.setOnClickListener {
            position++
            if (position > levelList.size - 1) {
                position = levelList.size - 1
            }
            setInfo()
        }
    }

    private fun setInfo() {
        binding.homeLabelLevel.text = levelList[position].title
        binding.homeLabelHighScore.text = getString(R.string.level__record_format, levelList[position].record)
        when (position) {
            0 -> {
                binding.homeImgNext.show()
                binding.homeImgBack.hide()
            }
            levelList.size - 1 -> {
                binding.homeImgNext.hide()
                binding.homeImgBack.show()
            }
            else -> {
                binding.homeImgNext.show()
                binding.homeImgBack.show()
            }
        }
    }

    private fun getNavController() = if (activity is NavHostActivity) {
        (activity as NavHostActivity).getNavController()
    } else {
        null
    }
}