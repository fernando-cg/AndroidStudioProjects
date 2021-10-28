package es.fesac.practica2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Descomentar para probar layout de login y comentando la línea 9
        //setContentView(R.layout.fragment_login)

        // Descomentar para probar layout de registro y comentando la línea 9
        //setContentView(R.layout.fragment_login)
    }
}