package es.fesac.practica1.model

import kotlin.math.ceil

/**
 * TODO Ejercicio 1
 */
//Creo las variables de esta clase

class Order(var idO:Long,var devices:Map<Int,Int>,var dnic:String,var dniv:String,var prize:Double){
    var points:Int = ceil(prize / 10).toInt() //Ejercicio 3 Propiedad que le da a la clase Order la cantidad de puntos que se le suman tras la compra buscar funcion de math para redondear
}
//Creo las variables de esta clase



