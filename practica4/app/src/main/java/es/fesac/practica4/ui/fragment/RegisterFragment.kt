package es.fesac.practica4.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.fesac.practica4.databinding.FragmentRegisterBinding
import es.fesac.practica4.ui.activity.NavHostActivity

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        binding.registerTextHaveAccount.setOnClickListener {
            getNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        binding.registerBtnCreateAccount.setOnClickListener {
            getNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
        }

        binding.registerImgClose.setOnClickListener {
            getNavController()?.navigateUp()
        }
    }

    private fun getNavController() = if (activity is NavHostActivity) {
        (activity as NavHostActivity).getNavController()
    } else {
        null
    }
}