package com.example.ejercicioclase01.model

class Client(name: String, surname: String = "", dni: String = "", var id: Long, var promotion: Int? = null) :Person(name, surname, dni) //asi se pone un valor por defecto
