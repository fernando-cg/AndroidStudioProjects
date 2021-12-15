package es.fesac.practica5.ui.extension

import androidx.constraintlayout.widget.ConstraintLayout
import es.fesac.practica5.databinding.CustomViewLoadingBinding

fun ConstraintLayout.visibility(binding : CustomViewLoadingBinding?,visibility:Int){
    binding?.card2?.visibility = visibility
    binding?.card0?.visibility = visibility
    binding?.card4?.visibility = visibility
    binding?.card8?.visibility = visibility
}