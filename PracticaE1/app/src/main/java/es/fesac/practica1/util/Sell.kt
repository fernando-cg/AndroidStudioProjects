package es.fesac.practica1.util

import es.fesac.practica1.model.Client
import es.fesac.practica1.model.Device
import es.fesac.practica1.model.Order


interface Sell {//Creo la interfaz con los parametros que recibe y con lo que nos devuelve como resultado
    fun sell(devices:Map<Device,Int>,client:Client):Order
}