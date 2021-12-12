package es.fesac.practica5.ui.fragment

import androidx.fragment.app.Fragment
import es.fesac.practica5.ui.activity.NavHostActivity

open class BaseFragment : Fragment() {
    fun getNavController() = if (activity is NavHostActivity) {
        (activity as NavHostActivity).getNavController()
    } else {
        null
    }
}