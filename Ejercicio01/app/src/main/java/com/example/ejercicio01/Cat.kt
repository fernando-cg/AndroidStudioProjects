package com.example.ejercicio01

import android.util.Log

class Cat (name: String, age: Int) : Animal(name, age),IMakeSound{
    override fun makeNoise() {
        Log.e("Sonido","Miau!")
    }

}