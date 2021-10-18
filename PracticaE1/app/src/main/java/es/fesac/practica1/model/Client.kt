package es.fesac.practica1.model

import java.util.*

/**
 * TODO Ejercicio 1
 */
class Client(dni: String, name: String, surname: String, born: Date,var idC:Long,
             var points:Int) :Person(dni, name, surname, born)//Parametros clase padre+clase Client