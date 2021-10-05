package com.example.ejercicioclase01.model

open class Vehicle(
    val rack: Long,
    val plate: String,
    val dniClient: String,
    var dniMechanic: String,
    var nHours: Int
){
    fun reparado() = when(this.nHours){
        0-> true
        else -> false
    }
}