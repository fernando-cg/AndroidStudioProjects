package es.fesac.practica5.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import es.fesac.practica5.R
import es.fesac.practica5.databinding.NavHostActivityBinding

class NavHostActivity : AppCompatActivity() {

    private var binding : NavHostActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavHostActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    fun showLoading(loading: Boolean) {
        // TODO 5.3 cambiar la forma de enviar los mensajes de errores y los saltos de contexto
        if(loading){
            binding?.navHostViewLoading?.showSquare()
            binding?.navHostViewLoading?.visibility = View.VISIBLE
        }
        else{
            binding?.navHostViewLoading?.hideSquare()
            binding?.navHostViewLoading?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}