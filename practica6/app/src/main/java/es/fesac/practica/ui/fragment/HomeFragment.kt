package es.fesac.practica.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import es.fesac.practica.R
import es.fesac.practica.databinding.FragmentHomeBinding
import es.fesac.practica.ui.extension.hide
import es.fesac.practica.ui.extension.show
import es.fesac.practica.ui.extension.showToast
import es.fesac.practica.ui.model.LevelVo
import es.fesac.practica.ui.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private var levelList = listOf<LevelVo>()
    private var position = 0

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoadingState()
        observeLoginState()
        observeErrorState()
        observeLevelListState()
    }

    private fun observeErrorState() {
        viewModel.getErrorLiveData().removeObservers(this)
        viewModel.getErrorLiveData().observe(this, { messageToShow ->
            messageToShow?.let {
                showToast(messageToShow)
            }
        })
    }

    private fun observeLoginState() {
        viewModel.getLogoutLiveData().removeObservers(this)
        viewModel.getLogoutLiveData().observe(this, { logout ->
            // TODO 6.3
            if(logout){
                setLogoutBtn()
            }
        })
    }

    private fun observeLoadingState() {
        viewModel.getLoadingLiveData().removeObservers(this)
        viewModel.getLoadingLiveData().observe(this, { loading ->
            showLoading(loading)
        })
    }

    private fun observeLevelListState() {
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
        binding.homeImgLogout.setOnClickListener{
            if(viewModel.isLoggedUser()){
                viewModel.logout()
            }else{
                goToLogin()
            }
        }
        setLogoutBtn()
    }

    private fun goToLogin() {
        getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    /**
     * TODO 6.3:
     * El icono que aparece en la imagen debe alternarse entre dos estados y debe efectuar una acción para cada uno de ellos:
     * - Estado no logueado (Recurso ic_cloud_white). Al pulsar se navegará a la pantalla de login. Si nos logueamos correctamente aparecerá el icono como el estado logueado.
     * - Estado logueado (Recurso ic_open_cloud_white). Al pulsar se hará un logout y se pondrá el icono como el estado no logueado si ha ido bien.
     */
    private fun setLogoutBtn() {
        // TODO 6.3
        if(viewModel.isLoggedUser()){
            binding.homeImgLogout.setImageResource(R.drawable.ic_open_cloud_white)
        }else{
            binding.homeImgLogout.setImageResource(R.drawable.ic_cloud_white)
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
}