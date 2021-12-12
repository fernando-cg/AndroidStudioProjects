package es.fesac.practica5.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import es.fesac.practica5.databinding.FragmentLoginBinding
import es.fesac.practica5.ui.viewmodel.LoginViewModel

/**
 * TODO 5.1:
 * Se pretende crear un ViewModel para la pantalla de Login, dicho ViewModel debe tener informar al fragment por tres LiveData:
 * Controla cuando se debe mostrar y ocultar la vista de carga.
 * Controla si el login es correcto o no, se debe:
 * Si el login es correcto se navegará a la pantalla de HomeFragment usando el método “goToHome()”.
 * Si el login no es correcto n ose hará nada.
 * Controla si se debe mostrar o no un mensaje de error. Se pueden dar varios casos:
 * El mensaje es nulo  No se mostrará nada.
 * El mensaje NO es nulo  Se mostrará un Toast usando el método “showToast()”.
 */
class LoginFragment : BaseFragment() {

    private var binding: FragmentLoginBinding? = null

    private val viewModel : LoginViewModel by viewModels()

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
            viewModel.login(getUserFromInput(), getPasswordFromInput())
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