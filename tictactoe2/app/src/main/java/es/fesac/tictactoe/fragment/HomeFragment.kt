package es.fesac.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import es.fesac.tictactoe.NavHostActivity
import es.fesac.tictactoe.databinding.FragmentHomeBinding
import es.fesac.tictactoe.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.multiplayerLiveData.removeObservers(this)
        viewModel.multiplayerLiveData.observe(this, { multiplayerState ->
            val multiplayerVisibility = if (multiplayerState) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.mainBtnMultiplayer.visibility = multiplayerVisibility
        })

        viewModel.loadingLiveData.removeObservers(this)
        viewModel.loadingLiveData.observe(this, { loading ->
            showLoading(loading)
        })
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
        viewModel.getMultiplayerState()
    }

    private fun setUpViews() {

        binding.mainBtnMultiplayer.setOnClickListener {
            goToGame("multi")
        }
        binding.mainBtnSinglePlayer.setOnClickListener {
            goToGame("single")
        }
    }
    private fun goToGame(gameType: String){
        getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment(gameType = gameType))
    }
    private fun getNavController(): NavController? {
        return (activity as? NavHostActivity)?.getNavController()
    }
}