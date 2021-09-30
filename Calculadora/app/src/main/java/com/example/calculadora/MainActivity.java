package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText textonombre,textoedad,textoapellidos;
    TextView inicio , nombre, edad, apellidos,resultado ;
    Button Enviar, atras ;
    ConstraintLayout fondo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //linkear elementos
        textonombre = findViewById(R.id.textonombre) ;
        textoedad = findViewById(R.id.textoedad) ;
        textoapellidos = findViewById(R.id.textoapellidos) ;

        inicio = findViewById(R.id.inicio) ;
        nombre = findViewById(R.id.nombre) ;
        edad = findViewById(R.id.edad) ;
        apellidos = findViewById(R.id.apellidos) ;
        resultado = findViewById(R.id.Resultado) ;

        Enviar = findViewById(R.id.Enviar) ;
        atras = findViewById(R.id.volveratras) ;

        fondo = findViewById(R.id.fondo) ;
    }

    public void pulsarBoton(View view){

        fondo.setBackgroundColor(Color.GREEN);

        String nombre = textonombre.getText().toString();
        int edad = Integer.parseInt(textoedad.getText().toString()) ;
        String apellidos = textoapellidos.getText().toString() ;

        textonombre.setVisibility(View.INVISIBLE);
        textoedad.setVisibility(View.INVISIBLE);
        textoapellidos.setVisibility(View.INVISIBLE);

        Enviar.setVisibility(View.INVISIBLE);

        this.inicio.setText("Resultado:");
        this.nombre.setText("Nombre: " + nombre);
        this.edad.setText("Edad: " + edad);
        this.apellidos.setText("Apellidos: " + apellidos);

        if(edad >= 18){
            resultado.setText("Eres mayor de edad");
        }else{
            resultado.setText("Eres menor de edad");
        }

        resultado.setVisibility(View.VISIBLE);

        atras.setVisibility(View.VISIBLE);
    }

    public void pulsarBoton2(View view){

        fondo.setBackgroundColor(Color.parseColor("#448AFF"));

        this.inicio.setText("Formulario");
        this.nombre.setText("Nombre");
        this.edad.setText("Edad");
        this.apellidos.setText("Apellidos");
        textonombre.setText("");
        textoedad.setText("");
        textoapellidos.setText("");

        textonombre.setVisibility(View.VISIBLE);
        textoedad.setVisibility(View.VISIBLE);
        textoapellidos.setVisibility(View.VISIBLE);

        Enviar.setVisibility(View.VISIBLE);

        resultado.setVisibility(View.INVISIBLE);

        atras.setVisibility(View.INVISIBLE);
    }
}