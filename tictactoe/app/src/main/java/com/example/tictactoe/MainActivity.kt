package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //viewBInding para que funcionen los botones
    private lateinit var binding: ActivityMainBinding //creo una variable de este tipo esto es para vincular interfaz con codigo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)//Aqui escribimos el codigo arriba o abajo dependiendo de si queremos hacer algo antes de que abra la pantalla

        binding = ActivityMainBinding.inflate(layoutInflater)//Con esto inflamos/abrimos la activitie layoutInflater es un propiedad extendida muy importante

        setupView()

        setContentView(binding.root) //asi le damos contenido al content view

    }

    private fun setupView() { //Metodo con el que vamos a configurar las vistas
        binding.mainBtnSinglePlayer.setOnClickListener {
            Toast.makeText(this,"Single player click",Toast.LENGTH_LONG).show() //esto es mostrar por pantalla un mensaje con ese texto y con x duracion en milisegundos
            binding.title.text = getString(R.string.app_name) //Con esto lo que haces es acceder a los recursos R es una clase interna que referencia a losrecursos despues se pone el recurso y despues la id del recurso
        } //esto es una funcion landa -> funcion que se produce cuando pasa x cosa en este caso cuando el usuario clique en este boton

        binding.mainBtnMultiplayer.setOnClickListener {
            Toast.makeText(this,"Multi player",Toast.LENGTH_SHORT).show()
            binding.title.setTextColor(ResourcesCompat.getColor(resources,R.color.white,null))
            binding.title.text = "Manolo"
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume(){//Ciclo de vida de la activitie -> metodos que se ejecutan en funcion de la pantalla onCreate->solo se crea y se muestra/onResume->permite interactuar con la pantalla/onPause -> pausar una vista cuando la abandonamos/onStop -> lo llama el sistema operativo y para la pantalla /onDestroy -> Destrulle la pantalla
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}