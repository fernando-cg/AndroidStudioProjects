package com.example.ejercicioclase2.Util

import com.example.ejercicioclase2.Model.Appliance
import com.example.ejercicioclase2.Model.Van
import com.example.ejercicioclase2.Model.Vehicle

fun maxWith(vehicles:List<Vehicle>):Vehicle{
    var maximun = 0F
    for(i in vehicles){
        if(i.maxWith > maximun){
            maximun = i.maxWith
        }
    }
    var vehicle = vehicles[0]

    for(i in vehicles){
        if(i.maxWith == maximun){
            vehicle = i
            break
        }
    }

    return vehicle
}

fun maxPrice(appliances:List<Appliance>): Appliance{
    var maximun = 0F
    for(i in appliances){
        if(i.price > maximun){
            maximun = i.price
        }
    }
    var appliance = appliances[0]

    for(i in appliances){
        if(i.price == maximun){
            appliance = i
            break
        }
    }

    return appliance
}