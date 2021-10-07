package com.example.ejercicio01

open class Person(
    var name: String,
    var surname: String,
    var edad: Int
){
    override fun equals(other: Any?): Boolean {
        return this.name == (other as Person)?.name // Esto hace que cuando comparemos dos cosas me coja como valor de referencia el nombre en este caso
    }
}