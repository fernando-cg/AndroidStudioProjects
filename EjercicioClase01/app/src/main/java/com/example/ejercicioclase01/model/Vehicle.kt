package com.example.ejercicioclase01.model

open class Vehicle(
    val rack: Long,
    val plate: String,
    val dniClient: String,
    var dniMechanic: String,
    var nHours: Int
){
    fun isRepaired() = nHours == 0
}