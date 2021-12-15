package es.fesac.practica5.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import es.fesac.practica5.R
import es.fesac.practica5.databinding.NavHostActivityBinding
import es.fesac.practica5.ui.extension.invisible
import es.fesac.practica5.ui.extension.visible

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
        // TODO 5.3
        if(loading){
            binding?.navHostViewLoading?.statusSquare(VISIBLE)
            binding?.navHostViewLoading?.visible()
        }
        else{
            binding?.navHostViewLoading?.statusSquare(GONE)
            binding?.navHostViewLoading?.invisible()
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}