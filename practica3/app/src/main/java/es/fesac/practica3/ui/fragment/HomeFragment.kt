package es.fesac.practica3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.fesac.practica3.databinding.FragmentHomeBinding
import es.fesac.practica3.ui.activity.NavHostActivity

class HomeFragment : Fragment() {

    // TODO 3.1: Inicializar binding
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // TODO 3.1: Inicializar binding
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        // TODO 3.2: Eventos de click y sus correspondientes navegaciones
        binding.homeImgLogout.setOnClickListener { getNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment()) }
    }

    private fun getNavController() = if (activity is NavHostActivity) {
        (activity as NavHostActivity).getNavController()
    } else {
        null
    }
}