package com.example.ejercicioclase01.util

import com.example.ejercicioclase01.model.Car
import com.example.ejercicioclase01.model.Client
import com.example.ejercicioclase01.model.Mechanic
import com.example.ejercicioclase01.model.Motorbike
import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("dd/MM/yyyy")

fun initClientList(): List<Client> {

    val clientList = mutableListOf<Client>()
    clientList.add(Client("Desiree","Carpio","84962675P",1,20))

    return clientList
}

fun initmechanicList(): List<Mechanic> {

    val mechanicList = mutableListOf<Mechanic>()
    mechanicList.add(Mechanic("Marina" ,"Yague","12631515F","7AFK7ND8", dateFormat.parse("10/05/2009")?: Calendar.getInstance().time)) //si la fecha primera es nula pon esta fecha por defecto -> operador Elvis

    return mechanicList
}

fun initCarList(): List<Car> {

    val CarList = mutableListOf<Car>()
    CarList.add(Car(6 ,"7398 LJZ","22094758S","12631515F",20,3))

    return CarList
}

fun initBikeList(): List<Motorbike> {

    val MotorbikeList = mutableListOf<Motorbike>()
    MotorbikeList.add(Motorbike(1 ,"3583 YXN","84962675P","12631515F",15,true))

    return MotorbikeList
}