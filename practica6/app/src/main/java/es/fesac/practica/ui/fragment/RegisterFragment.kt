package es.fesac.practica.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import es.fesac.practica.databinding.FragmentRegisterBinding
import es.fesac.practica.ui.extension.showToast
import es.fesac.practica.ui.viewmodel.RegisterViewModel

/**
 * TODO 6.2:
 * Implementar el registro, para ello se deberá:
 * - 3 LiveData para controlar, loading, registro correcto y error. En caso de que el registro sea correcto, se navegará a la home.
 * - Validaciones de datos:
 *      * Email vacío   register_error__empty_email
 *      * Password vacía  register_error__empty_password
 *      * Las contraseñas no coinciden  register_error__password_repeat
 *      * Usuario vacío  register_error__empty_user
 *      * Check no marcado  register_error__accept_term_and_conditions
 */
class RegisterFragment : BaseFragment() {

    private var binding: FragmentRegisterBinding? = null
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO 6.2
        observeLoadingState()
        observeRegisterState()
        observeErrorState()

    }

    private fun observeErrorState() {
        // TODO 6.2
        viewModel.getErrorLiveData().removeObservers(this)
        viewModel.getErrorLiveData().observe(this,{ message ->
            if (message != null) {
                showToast(message)
            }
        })
    }

    private fun observeRegisterState() {
        // TODO 6.2
        viewModel.getRegisterLiveData().removeObservers(this)
        viewModel.getRegisterLiveData().observe(this,{ status ->
            if(status){
                goToHome()
            }
        })
    }

    private fun observeLoadingState() {
        // TODO 6.2
        viewModel.getLoadingLiveData().removeObservers(this)
        viewModel.getLoadingLiveData().observe(this,{ status ->
            showLoading(status)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setUpViews()
        return binding?.root
    }

    private fun setUpViews() {
        binding?.registerTextHaveAccount?.setOnClickListener {
            getNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        binding?.registerBtnCreateAccount?.setOnClickListener {
            viewModel.register(
                email = getEmail(),
                password = getPassword(),
                passwordRepeat = getPasswordRepeat(),
                user = getUser(),
                check = acceptTermCheck()
            )
        }

        binding?.registerImgClose?.setOnClickListener {
            getNavController()?.navigateUp()
        }
    }

    private fun goToHome() {
        getNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
    }

    private fun getEmail() = binding?.registerInputEmail?.text.toString()

    private fun getPassword() = binding?.registerInputPassword?.text.toString()

    private fun getPasswordRepeat() = binding?.registerInputPasswordRepeat?.text.toString()

    private fun getUser() = binding?.registerInputUser?.text.toString()

    private fun acceptTermCheck() = binding?.registerCheckAcceptTerms?.isChecked ?: false

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}