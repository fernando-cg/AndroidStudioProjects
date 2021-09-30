package com.example.ejercicio01

class Teacher(
    name: String,
    surname: String,
    edad: Int,
    var classroomManage: String?, //asi se pone que esta variable sea nulable
    var isManager: Boolean
) : Person(name, surname, edad)
