package com.example.ejercicioclase01.model

import java.util.*

class Mechanic(
    name: String,
    surname: String,
    dni: String,
    var mechanicId: String,
    var dateFrom: Date
) : Person(name, surname, dni){

    fun arreglarcoche(car: Car){
        car.setNhours(0)
    }
}