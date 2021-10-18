package es.fesac.practica1.model

import es.fesac.practica1.util.Sell
import es.fesac.practica1.util.updateClientPoints

import java.util.*


/**
 * TODO Ejercicio 1
 */
class Seller(dni: String, name: String, surname: String, born: Date, var idS:Long) :Person(dni, name,
    surname, born), Sell{
    //Interfaz ejercicio 5
    override fun sell(devices: Map<Device, Int>, client: Client): Order {
        var idCount = mutableMapOf<Int,Int>()
        var totalPrize = 0.0
        for(i in devices){
            idCount[i.key.id] = i.value
            totalPrize += i.key.prize
        }
        val order = Order((Math.random()*1000).toLong(),idCount,client.dni,this.dni,totalPrize)
        updateClientPoints(listOf(client), listOf(order))
        return order
    }

}//Parametros clase padre+clase Seller
