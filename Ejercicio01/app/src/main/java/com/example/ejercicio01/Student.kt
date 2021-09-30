package com.example.ejercicio01

class Student(
    name: String,
    surname: String,
    edad: Int
) : Person(name, surname, edad) {
    //extension de una clase y los parametros que se envian a la clase padre
    override fun toString(): String {
        return "Student(name='$name', surname='$surname', edad=$edad)"
    }

    fun toString2() = "Student(name='$name', surname='$surname', edad=$edad)"

    fun test(vararg arguments: String) {
        //Esto lo que hace es que recibe una cantidad indefinida de argumentos y se guarda en una array a demas asi es una funcion void

    }
}