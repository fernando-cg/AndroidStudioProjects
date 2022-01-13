package es.fesac.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import es.fesac.tictactoe.databinding.FragmentLoginBinding
import es.fesac.tictactoe.extension.showToast
import es.fesac.tictactoe.viewmodel.LoginViewModel

class LoginFragment : BaseFragment() {
    private var binding: FragmentLoginBinding? = null
    private val viewModel:LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO observers
        setLoadingObserver()
        setLoginSuccessObserver()
        setErrorObserver()
    }

    private fun setErrorObserver() {
        viewModel.errorLiveData().removeObservers(this)
        viewModel.errorLiveData().observe(this,{ error ->
            error?.let{
                showToast(error)
            }
        })
    }

    private fun setLoginSuccessObserver() {
        viewModel.successLoginLiveData().removeObservers(this)
        viewModel.successLoginLiveData().observe(this,{
            if(it){
                goToHome()
            }
        })
    }

    private fun goToHome() {
       getNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun setLoadingObserver() {
       viewModel.loadingLiveData().removeObservers(this)
       viewModel.loadingLiveData().observe(this,{
           showLoading(it)
       })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setUpViews()
        return binding?.root
    }

    private fun setUpViews() {
        // TODO configure views
        binding?.loginBtnEnter?.setOnClickListener {
            viewModel.login(getEmail(),getPassword())
        }
    }

    private fun getEmail():String{
        return binding?.loginInputUser?.text.toString()
    }

    private fun getPassword():String{
        return binding?.loginInputPassword?.text.toString()
    }
    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}