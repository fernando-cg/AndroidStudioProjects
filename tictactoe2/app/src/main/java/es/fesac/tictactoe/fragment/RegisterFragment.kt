package es.fesac.tictactoe.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import es.fesac.tictactoe.R
import es.fesac.tictactoe.databinding.FragmentRegisterBinding
import es.fesac.tictactoe.extension.showToast
import es.fesac.tictactoe.viewmodel.RegisterViewModel

class RegisterFragment : BaseFragment() {
    private var binding: FragmentRegisterBinding? = null
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLoadingObserver()
        setLoginSuccessObserver()
        setErrorObserver()
    }

    private fun setErrorObserver() {
        viewModel.errorLiveData().removeObservers(this)
        viewModel.errorLiveData().observe(this, { error ->
            error?.let {
                showToast(error)
            }
        })
    }

    private fun setLoginSuccessObserver() {
        viewModel.successRegisterLiveData().removeObservers(this)
        viewModel.successRegisterLiveData().observe(this, { success ->
            if (success) {
                goToHome()
            }
        })
    }

    private fun goToHome() {
        getNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
    }

    private fun setLoadingObserver() {
        viewModel.loadingLiveData().removeObservers(this)
        viewModel.loadingLiveData().observe(this, { loading ->
            showLoading(loading)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        setUpViews()
        return binding?.root
    }

    private fun setUpViews() {
        binding?.registerBtnCreateAccount?.setOnClickListener {
            viewModel.register(
                context = requireContext(),
                user = getUser(),
                email = getEmail(),
                check = isCheck(),
                password = getPassword()
            )
        }
        setUpPasswordValidator()
    }

    private fun setUpPasswordValidator() {
        binding?.registerInputPasswordRepeat?.addTextChangedListener {
            val passwordRepeat = it.toString()
            val match = getPassword().contains(passwordRepeat, false)
            val colorResId = if (match) {
                R.color.black
            } else {
                R.color.red
            }
            binding?.registerInputPasswordRepeat?.setTextColor(ResourcesCompat.getColor(resources, colorResId, null))
        }
    }

    private fun getUser(): String {
        return binding?.registerInputUser?.text?.toString() ?: ""
    }

    private fun getEmail(): String {
        return binding?.registerInputEmail?.text?.toString() ?: ""
    }

    private fun getPassword(): String {
        return binding?.registerInputPassword?.text?.toString() ?: ""
    }

    private fun getPasswordRepeat(): String {
        return binding?.registerInputPasswordRepeat?.text?.toString() ?: ""
    }

    private fun isCheck(): Boolean {
        return binding?.registerCheckAcceptTerms?.isChecked ?: false
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}