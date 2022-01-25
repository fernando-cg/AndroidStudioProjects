package es.fesac.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import es.fesac.tictactoe.NavHostActivity
import es.fesac.tictactoe.R
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
        setLogoutSuccessObserver()
    }

    private fun setLogoutSuccessObserver() {
        viewModel.logoutSuccessLiveData().removeObservers(this)
        viewModel.logoutSuccessLiveData().observe(this, { logoutSuccess ->
            if (logoutSuccess) {
                setUpLoginBtn()
            }
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
        binding.mainBtnLogin.setOnClickListener {
            if (viewModel.isUserLogged()) {
                viewModel.logout()
            } else {
                goToLogin()
            }
        }
        binding.mainBtnHistory.setOnClickListener {
            goToHistory()
        }
        setUpLoginBtn()
    }

    private fun goToHistory() {
        getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToHistoryFragment())
    }

    private fun setUpLoginBtn() {
        val textResId = if (viewModel.isUserLogged()) {
            R.string.logout
        } else {
            R.string.login
        }
        binding.mainBtnLogin.text = getString(textResId)
    }

    private fun goToLogin() {
        getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    private fun goToGame(gameType: String) {
        getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment(gameType = gameType))
    }
}