package com.example.ejercicioclase01.model

class Car(
    rack: Long,
    plate: String,
    dniClient: String,
    dniMechanic: String,
    nHours: Int,
    val NDoors: Int //se indica que es una variable de esta clase
) : Vehicle(
    rack, plate,
    dniClient, dniMechanic, nHours
)