package com.example.ejercicio01

fun Int.isPair(): Boolean{//esto significa que a la clase int le añado una nueva funcion
    var result = false

    if(this % 2 == 0){ //aqui el this significa que el this es yo como objeto es decir 11.funcion coge el 11
        result = true
    }
    return result
}
//Esto es en forma simple

fun Int.isPair2() = this % 2 == 0

fun Int?.isPair3() = this != null && this % 2 == 0 //Esto es lo mismo que arriba Pero tambien sirve por si la variable que entra es nulable entonces no me explota el nulo

//controlar los nulos es super importante

//Esto son las funciones estaticas y se meten en un fichero en la que se van a poder extender estas funciones

//Funciones de extension -> es una funcion que se crea para que se use en diferentes pantallas