package es.fesac.practica3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.fesac.practica3.databinding.FragmentLoginBinding
import es.fesac.practica3.databinding.FragmentRegisterBinding
import es.fesac.practica3.ui.activity.NavHostActivity

class LoginFragment : Fragment() {

    // TODO 3.1: Inicializar binding
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // TODO 3.1: Inicializar binding
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        // TODO 3.2: Eventos de click y sus correspondientes navegaciones

    }

    private fun getNavController() = if (activity is NavHostActivity) {
        (activity as NavHostActivity).getNavController()
    } else {
        null
    }
}