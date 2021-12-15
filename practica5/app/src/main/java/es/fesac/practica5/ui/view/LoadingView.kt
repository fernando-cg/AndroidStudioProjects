package es.fesac.practica5.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import es.fesac.practica5.databinding.CustomViewLoadingBinding

/**
 * TODO 5.3:
 * Se trata dela vista que contendrá la pantalla de carga, para ello se debe crear una CustomView usando el layout “custom_view_loading” ya creado en la práctica.
 * Se debe añadir bajo la carpeta “view” del proyecto, una vez realizado, el error que hay en el layout “nav_host_activity” debe desaparecer.
 * Para mostrar u ocultar la vista, es necesario acceder a la NavHostActivity desde el fragment.
 */
class LoadingView(context: Context, attrs: AttributeSet?): ConstraintLayout(context,attrs) {
    private var binding : CustomViewLoadingBinding? = null
    //Init the custom view
    init {
        binding = CustomViewLoadingBinding.inflate(LayoutInflater.from(context),this,true)
    }

    override fun onDetachedFromWindow() {
        binding = null
        super.onDetachedFromWindow()
    }
    fun statusSquare(visibility:Int){
        binding?.card2?.visibility = visibility
        binding?.card0?.visibility = visibility
        binding?.card4?.visibility = visibility
        binding?.card8?.visibility = visibility
    }
}