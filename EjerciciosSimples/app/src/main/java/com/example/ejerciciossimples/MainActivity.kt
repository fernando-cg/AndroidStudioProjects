package com.example.ejerciciossimples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list = mutableListOf(6,1,7,2,3,9,4,5,10,8)

        for(i in 0 until list.size){
            list[i] = list[i]*2
        }

        Log.e("AndroidPrueba",list.toString())


    }

}