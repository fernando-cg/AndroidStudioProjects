package es.fesac.practica1.model

import es.fesac.practica1.util.Sell
import es.fesac.practica1.util.updateClientPoints

import java.util.*


/**
 * TODO Ejercicio 1
 */
class Seller(dni: String, name: String, surname: String, born: Date, var idS:Long) :Person(dni, name,
    surname, born), Sell{

    //Interfaz ejercicio 5 la desarrollo para que haga la funcion para ello lo que hago es transformar el mapa y coger la suma de los precios de los productos
    override fun sell(devices: Map<Device, Int>, client: Client): Order {
        var totalPrize = 0.0

        for(i in devices){
            totalPrize += i.key.prize*i.value //COn esto multiplicamos el numero de veces de cada elemento por el precio
        }

        val order = Order((Math.random()*1000).toLong(),devices.mapKeys { it.key.id },client.dni,this.dni,totalPrize)
        updateClientPoints(listOf(client), listOf(order))
        return order
    }

}//Parametros clase padre+clase Seller
