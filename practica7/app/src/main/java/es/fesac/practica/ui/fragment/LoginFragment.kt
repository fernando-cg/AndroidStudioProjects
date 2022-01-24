package es.fesac.practica.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import es.fesac.practica.databinding.FragmentLoginBinding
import es.fesac.practica.ui.extension.hideSoftKeyboard
import es.fesac.practica.ui.extension.showToast
import es.fesac.practica.ui.viewmodel.LoginViewModel

class LoginFragment : BaseFragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoadingState()
        observeLoginState()
        observeErrorState()
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
        viewModel.getLoginLiveData().removeObservers(this)
        viewModel.getLoginLiveData().observe(this, { logged ->
            binding?.loginBtnEnter?.hideSoftKeyboard()
            if (logged) {
                goToHome()
            }
        })
    }

    private fun observeLoadingState() {
        viewModel.getLoadingLiveData().removeObservers(this)
        viewModel.getLoadingLiveData().observe(this, { loading ->
            showLoading(loading)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpViews()
        return binding?.root
    }

    private fun setUpViews() {
        binding?.loginTextNoHaveAccount?.setOnClickListener {
            goToRegister()
        }

        binding?.loginBtnEnter?.setOnClickListener {
            //Preguntar esto
            viewModel.login(requireContext(),getUserFromInput(), getPasswordFromInput())
        }

        binding?.loginImgClose?.setOnClickListener {
            goToBack()
        }
    }

    private fun getUserFromInput(): String {
        return binding?.loginInputUser?.text.toString()
    }

    private fun getPasswordFromInput(): String {
        return binding?.loginInputPassword?.text.toString()
    }

    private fun goToHome() {
        getNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun goToRegister() {
        getNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun goToBack() {
        getNavController()?.navigateUp()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}