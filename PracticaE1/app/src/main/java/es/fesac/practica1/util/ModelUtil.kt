package es.fesac.practica1.util

import android.text.TextUtils.indexOf
import es.fesac.practica1.model.*
import java.text.SimpleDateFormat

val dateFormat = SimpleDateFormat("dd/MM/yyyy")

/**
 * TODO Ejercicio 2.a
 */
fun generateClientAndSellerList(): List<Person> = listOf(Client("62062198X","Marc","Galera",dateFormat.parse("14/06/1991"),1,0),
    Client("89124427W","Noel","Romo",dateFormat.parse("03/05/1990"),2,0),
    Client("30551701L","Jacobo","Torre",dateFormat.parse("18/01/1991"),3,0),
    Client("88289642W","Nerea","Hernández",dateFormat.parse("28/08/2002"),4,0),
    Client("02110836B","Ainara","Melgar",dateFormat.parse("26/11/1986"),5,0),
    Client("41371814N","Sergio","Prat",dateFormat.parse("06/07/1983"),6,0),
    Seller("79711552S","Almudena","Caro",dateFormat.parse("10/06/1993"),1),
    Seller("45095419D","Ingrid","Carbonell",dateFormat.parse("03/07/1992"),2),
    Seller("43702270Q","Saray","Garriga",dateFormat.parse("28/01/1995"),3),
    Seller("60479732J","Jaume","Berrocal",dateFormat.parse("21/05/2001"),4),
    Seller("73155983K","Ángel","Chacón",dateFormat.parse("20/10/1988"),5),
    Seller("88154456X","Hugo","Bolaños",dateFormat.parse("11/03/1985"),6)
    ) //Funcion Primera lista de personas

/**
 * TODO Ejercicio 2.b
 */
fun generateMobileAndTabletList(): List<Device> = listOf(Mobile(1,349.97,"Xiaomi","Mi 9T Pro",false),
    Mobile(2,518.99,"Oppo","Find X",false),
    Mobile(3,799.00,"Samsung","S20",true),
    Mobile(4,1159.00,"Apple","iPhone 12 Pro",true),
    Mobile(5,489.00,"Apple","iPhone SE",false),
    Mobile(6,299.00,"Samsung","A51",true),
    Tablet(7,1099.00,"Samsung","Tab S7+",true),
    Tablet(8,289.00,"Samsung","Tab A7",false),
    Tablet(9,879.50,"Apple","iPad Pro",true),
    Tablet(10,649.00,"Apple","iPad Air",true),
    Tablet(11,699.00,"Huawei","P40",false),
    Tablet(12,229.50,"Xiaomi","Note 9 Pro",false)

)//Lista de Dispositivos

/**
 * TODO Ejercicio 2.c
 */
fun generateOrderList(): MutableList<Order> = mutableListOf(
    Order(1, mapOf(9 to 1, 1 to 1),"62062198X","79711552S",1229.47),
    Order(2, mapOf(7 to 1, 4 to 1),"02110836B","45095419D",2258.00),
    Order(3, mapOf(12 to 2),"62062198X","43702270Q",459.00),
    Order(4, mapOf(3 to 4, 10 to 2),"30551701L","79711552S",4494.00),
    Order(5, mapOf(8 to 1),"62062198X","88154456X",289.00),
    Order(6, mapOf(3 to 1, 2 to 3),"30551701L","73155983K",2355.97),
    Order(7, mapOf(10 to 1),"89124427W","45095419D",649.00),
    Order(8, mapOf(2 to 1,5 to 2, 6 to 1,11 to 1),"41371814N","73155983K",2494.99),
    Order(9, mapOf(9 to 2),"02110836B","73155983K",1759.00)
)//Lista de pedidos

/**
 * TODO Ejercicio 4
 */
fun updateClientPoints(clientList: List<Client>, orderList: List<Order>) {
    for(i in orderList){

        clientList[clientList.indexOf(clientList.find { it-> it.dni==i.dnic })].points += i.points //Primero busco el elemento que tiene el mismo dni depues el index de ese elemento en la lista y ya por ultimo cogo ese elemento y le sumo los puntos de la lista
    }//Recorro la lista y voy comparando los dni gracias a la propiedad find que me los encuentra en la lista
}

/**
 * TODO Ejercicio 6
 */
fun bestSeller(sellerList: List<Seller>, orderList: List<Order>): Seller? { //Este nos encuentra el mejor vendedor para ello agrupo por dni
// Sumo todos los precios y despues lo meto en un sorted map para que se ordene

    var mapSeller = orderList.groupBy { it -> it.dniv }

    var mapPrize = mutableMapOf<Double,Seller>()

    for(i in mapSeller){
        i.value.sumOf { it -> it.prize }
        mapPrize[i.value.sumOf { it -> it.prize }] = sellerList[sellerList.indexOf(sellerList.find { it -> it.dni == i.value[0].dniv })]

    }

    val result = mapPrize.toSortedMap()

    return mapPrize[result.lastKey()]


}

/**
 * TODO Ejercicio 7
 */
fun orderClientByAccumulatedPoints(clientList: List<Client>): List<Client> = clientList.sortedByDescending { it.points } //Utilizo sortedby para ordenar por el campo que me interesa
