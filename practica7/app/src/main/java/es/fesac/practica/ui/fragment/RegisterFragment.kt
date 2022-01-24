package es.fesac.practica.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import es.fesac.practica.R
import es.fesac.practica.databinding.FragmentRegisterBinding
import es.fesac.practica.ui.extension.hideSoftKeyboard
import es.fesac.practica.ui.extension.showToast
import es.fesac.practica.ui.viewmodel.RegisterViewModel

class RegisterFragment : BaseFragment() {

    private var binding: FragmentRegisterBinding? = null
    private val viewModel: RegisterViewModel by viewModels()

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
        viewModel.getRegisterLiveData().removeObservers(this)
        viewModel.getRegisterLiveData().observe(this, { register ->
            binding?.registerBtnCreateAccount?.hideSoftKeyboard()
            if (register) {
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
                //Preguntar esto
                context=requireContext(),
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

        setUpPasswordInput()
    }

    private fun setUpPasswordInput() {
        binding?.registerInputPasswordRepeat?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // no-op
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val textColor = if (getPassword().contains(getPasswordRepeat(), ignoreCase = false)) {
                    R.color.text_black
                } else {
                    R.color.color_64
                }
                binding?.registerInputPasswordRepeat?.setTextColor(ResourcesCompat.getColor(resources, textColor, null))
            }

            override fun afterTextChanged(s: Editable?) {
                // no-op
            }
        })
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