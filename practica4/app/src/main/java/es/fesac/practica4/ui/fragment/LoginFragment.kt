package es.fesac.practica4.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.fesac.practica4.databinding.FragmentLoginBinding
import es.fesac.practica4.ui.activity.NavHostActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        binding.loginTextNoHaveAccount.setOnClickListener {
            getNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.loginBtnEnter.setOnClickListener {
            getNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }

        binding.loginImgClose.setOnClickListener {
            getNavController()?.navigateUp()
        }
    }

    private fun getNavController() = if (activity is NavHostActivity) {
        (activity as NavHostActivity).getNavController()
    } else {
        null
    }
}