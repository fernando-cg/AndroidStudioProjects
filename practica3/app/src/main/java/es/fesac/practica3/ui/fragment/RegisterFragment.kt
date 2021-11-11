package es.fesac.practica3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.fesac.practica3.databinding.FragmentRegisterBinding
import es.fesac.practica3.ui.activity.NavHostActivity

class RegisterFragment : Fragment() {

    // TODO 3.1: Inicializar binding
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // TODO 3.1: Inicializar binding
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        fragmentManager?.fragments
        // TODO 3.2: Eventos de click y sus correspondientes navegaciones
        binding.registerImgClose.setOnClickListener { getNavController()?.navigateUp() }
        binding.registerBtnCreateAccount.setOnClickListener { getNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()) }
        binding.registerTextHaveAccount.setOnClickListener { getNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()) }
    }

    private fun getNavController() = if (activity is NavHostActivity) {
        (activity as NavHostActivity).getNavController()
    } else {
        null
    }
}