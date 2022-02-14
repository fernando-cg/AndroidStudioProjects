package es.fesac.practica.ui.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(textToShow: String, duration: Int = Toast.LENGTH_SHORT) {
    activity?.let {
        Toast.makeText(context, textToShow, duration).show()
    }
}

fun Fragment.showToast(textToShow: Int, duration: Int = Toast.LENGTH_SHORT) {
    activity?.let {
        Toast.makeText(context, activity?.getString(textToShow), duration).show()
    }
}