package com.example.conecsionbbdd;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    TextView text,errorText;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        errorText = (TextView) findViewById(R.id.errorText);
        show = (Button) findViewById(R.id.show);

        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //new Async().execute();
                new Task().execute();
            }
        });
    }

    class Task extends AsyncTask<Void, Void, Void> {

        String records = "",error="";

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://134.209.90.70:3306/clase", "user", "fesac_2021APP");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM alumnos");
                while(resultSet.next()) {
                    records += resultSet.getString(1) + " " + resultSet.getString(2 ) + "\n";

                }
            } catch(Exception e){
                error = e.toString();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            text.setText(records);
            if(error != "")
                errorText.setText(error);
            super.onPostExecute(aVoid);
        }

    }

}