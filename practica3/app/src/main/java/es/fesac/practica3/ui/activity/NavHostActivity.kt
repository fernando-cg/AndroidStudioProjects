package es.fesac.practica3.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import es.fesac.practica3.R
import es.fesac.practica3.databinding.NavHostActivityBinding

class NavHostActivity : AppCompatActivity() {

    private lateinit var binding : NavHostActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavHostActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}