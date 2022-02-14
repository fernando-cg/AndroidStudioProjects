package es.fesac.tictactoe.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import es.fesac.tictactoe.NavHostActivity

open class BaseFragment : Fragment() {
    fun showLoading(loading: Boolean) {
        (activity as? NavHostActivity)?.showLoading(loading)
    }

    fun getNavController(): NavController? {
        return (activity as? NavHostActivity)?.getNavController()
    }
}