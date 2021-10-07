package com.example.ejercicioclase01.model

import java.util.*

class Painter(
    name: String,
    surname: String,
    dni: String,
    var painterID: String,
    var dateFrom: Date
) : Person(name, surname, dni),IRepair {
    override fun repairVehicle(vehicle: Vehicle) {
        vehicle.nHours -= 2
    }
}