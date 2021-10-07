package com.example.ejercicioclase01.model

import java.util.*

class Mechanic(
    name: String,
    surname: String,
    dni: String,
    var mechanicId: String,
    var dateFrom: Date
) : Person(name, surname, dni), IRepair {
    override fun repairVehicle(vehicle: Vehicle) {
        vehicle.nHours = 0
    }
}


/*Asi no se hace porque esta funcion tambien la podria hacer un pintor si es una averia de pintura a si que se pondria en una interfaz
    fun arreglarcoche(car: Car){
        car.setNhours(0)

}*/