package com.example.ejercicioclase01.util

import com.example.ejercicioclase01.model.*
import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("dd/MM/yyyy")

fun generatePersonList(): List<Person> {
    val result = mutableListOf<Person>()
    //- Personas
    result.add(Client(name ="84962675P", id =1, promotion = 20)) //asi se envian las variables bien esto es para cuando se envian parametros y a donde va cada cosa
    result.add(Client("79956421A", "Sergi", "Agullo", 2))
    result.add(Client("04297948F", "Xabier", "Ribera", 3))
    result.add(Client("64407574F", "Montse", "Miranda", 4))
    result.add(Client("22094758S", "Amanda", "Ferrer", 5, 50))
    result.add(Client("43065983R", "Valentin", "Saldaña", 6))

    //- Mecánicos
    result.add(Mechanic("12631515F", "Marina", "Yague", "7AFK7ND8", dateFormat.parse("10/05/2009")?: Calendar.getInstance().time))
    result.add(Mechanic("47700725J", "Ernesto", "Bermejo", "FC57BK8H",dateFormat.parse("10/05/2009")?: Calendar.getInstance().time)))
    result.add(Mechanic("85989322G", "Isaac", "Andrade", "DVJWHN42",dateFormat.parse("10/05/2009")?: Calendar.getInstance().time) ))
    return result
}

fun generateVehicleList(): List<Vehicle> {
    val result = mutableListOf<Vehicle>()
    //- Motos
    result.add(Motorbike(1, "3583 YXN", "84962675P", "12631515F", 15, true))
    result.add(Motorbike(2, "2981 CDC", "79956421A", "12631515F", 10, false))
    result.add(Motorbike(3, "5054 RKX", "79956421A", "47700725J", 24, false))
    result.add(Motorbike(4, "6468 QCQ", "04297948F", "47700725J", 4, false))
    result.add(Motorbike(5, "9951 RFB", "64407574F", "47700725J", 8, false))

    //- Coches
    result.add(Car(16, "7398 LJZ", "22094758S", "12631515F", 20, 3))
    result.add(Car(17, "9967 MMK", "84962675P", "47700725J", 72, 5))
    result.add(Car(18, "0416 SPM", "64407574F", "85989322G", 10, 5))
    result.add(Car(19, "0854 FXR", "43065983R", "85989322G", 5, 3))
    result.add(Car(10, "9163 MRG", "79956421A", "85989322G", 19, 5))
    return result
}

fun toRepair(vehicleList: List<Vehicle>, personList: List<Person>) :Map<Client, List<Vehicle>>{ //Esperar a que fran explique esto

    val dniWithVehicleList = vehicleList.filter { vehicle ->
        vehicle.isRepaired().not()
    }.groupBy { vehicle ->
        vehicle.dniClient
    }

    return dniWithVehicleList.mapKeys { entry ->
        personList.find { person ->
            person is Client && person.dni == entry.key
        } as? Client ?: makeEmptyClient()
    }.filter { entry -> entry.key.dni != "" }
}

fun makeEmptyClient() = Client("", "", "", 0,null)

fun lessHours(mechanics:List<Mechanic>):Mechanic{

    var mechanic = mechanics[0]
    var minH = 0
    for(i in mechanics){
        if(mechanic.)
    }
}

fun ClientPrize(){

}