package es.fesac.practica5.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import es.fesac.practica5.R
import es.fesac.practica5.databinding.FragmentHomeBinding
import es.fesac.practica5.ui.model.LevelVo
import es.fesac.practica5.ui.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private var levelList = listOf<LevelVo>()
    private var position = 0

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLoadLevelsLiveData().removeObservers(this)
        viewModel.getLoadLevelsLiveData().observe(this, {
            levelList = it
            position = 0
            setInfo()
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
            getNavController()?.navigate(
                HomeFragmentDirections.actionHomeFragmentToGameFragment()
                    .setCellsNumber(levelList[position].cellsNumber))
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
                binding.homeImgNext.visibility = View.VISIBLE
                binding.homeImgBack.visibility = View.INVISIBLE
            }
            levelList.size - 1 -> {
                binding.homeImgNext.visibility = View.INVISIBLE
                binding.homeImgBack.visibility = View.VISIBLE
            }
            else -> {
                binding.homeImgNext.visibility = View.VISIBLE
                binding.homeImgBack.visibility = View.VISIBLE
            }
        }
    }
}