package com.example.fesac;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button  boton2,volver ;
    ImageButton play_pause ;
    MediaPlayer mp ;
    TextView tv1,datos ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton2 = (Button) findViewById(R.id.AddJugador) ;
        tv1 = (TextView) findViewById(R.id.tv1) ;
        mp = MediaPlayer.create(this,R.raw.betis) ;
        volver = findViewById(R.id.volver) ;
        datos = findViewById(R.id.datos) ;
        play_pause = findViewById(R.id.Play_pause) ;

    }

    class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {



            return null;

        }

    }

    public void pulsarboton (View view){

        if(!mp.isPlaying()) {
            mp.start();
        }
        else{
            mp.pause();
        }
    }

    public void mostrarJugadores(View view){

        tv1.setText("Te amo mucho");

        boton2.setVisibility(View.INVISIBLE);
        play_pause.setVisibility(View.INVISIBLE);
        datos.setVisibility(View.VISIBLE);
        volver.setVisibility(View.VISIBLE);
    }

    public void volver(View view){
        tv1.setText("Hola Amor");
        boton2.setVisibility(View.VISIBLE);
        play_pause.setVisibility(View.VISIBLE);
        datos.setVisibility(View.INVISIBLE);
        volver.setVisibility(View.INVISIBLE);
    }
}