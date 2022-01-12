package es.fesac.tictactoe.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(textToShow: String) {
    Toast.makeText(activity, textToShow, Toast.LENGTH_SHORT).show()
}