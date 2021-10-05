package com.example.ejercicioclase01.model

class Motorbike(
    rack: Long,
    plate: String,
    dniClient: String,
    dniMechanic: String,
    nHours: Int,
    var Sidecar: Boolean
) : Vehicle(
    rack, plate,
    dniClient, dniMechanic, nHours
)