package es.fesac.tictactoe

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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

    fun showLoading(loading: Boolean) {
        if (loading) {
            binding.navHostActivityGroupLoading.visible()
        } else {
            binding.navHostActivityGroupLoading.gone()
        }
    }

    fun getNavController(): NavController {
        return binding.navHostFragment.findNavController()
    }
}