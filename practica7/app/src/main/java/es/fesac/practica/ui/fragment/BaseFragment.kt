package es.fesac.practica.ui.fragment

import androidx.fragment.app.Fragment
import es.fesac.practica.ui.activity.NavHostActivity

open class BaseFragment : Fragment() {
    fun getNavController() = if (activity is NavHostActivity) {
        (activity as NavHostActivity).getNavController()
    } else {
        null
    }

    fun showLoading(loading: Boolean) {
        if (activity is NavHostActivity) {
            (activity as NavHostActivity).showLoading(loading)
        }
    }
}