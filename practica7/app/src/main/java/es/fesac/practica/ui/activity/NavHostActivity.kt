package es.fesac.practica.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import es.fesac.practica.R
import es.fesac.practica.databinding.NavHostActivityBinding
import es.fesac.practica.ui.extension.hide
import es.fesac.practica.ui.extension.show

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
        if (loading) {
            binding?.navHostViewLoading?.show()
            binding?.navHostViewLoading?.start()
        } else {
            binding?.navHostViewLoading?.stop()
            binding?.navHostViewLoading?.hide()
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}