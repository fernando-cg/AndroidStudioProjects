package es.fesac.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import es.fesac.tictactoe.databinding.NavHostActivityBinding
import es.fesac.tictactoe.extension.gone
import es.fesac.tictactoe.extension.visible

class NavHostActivity : AppCompatActivity() {

    private lateinit var binding: NavHostActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = NavHostActivityBinding.inflate(layoutInflater)

        setUpViews()

        setContentView(binding.root)
    }

    private fun setUpViews() {

    }

    fun showLoading(loading:Boolean){
        if(loading){
            binding.navHostActivityGroupLoading.visible()
        }else{
            binding.navHostActivityGroupLoading.gone()
        }
    }

    fun getNavController(): NavController {
        return binding.navHostFragment.findNavController()
    }
}