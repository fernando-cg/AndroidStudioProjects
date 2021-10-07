package com.example.ejercicioclase2.Model

class WashingMachine(sku: Int,price: Float,weight: Float,var maxWith: Int,var velocit: Int): Appliance(sku,price,weight){
    fun priceWM(){
        if(this.velocit> 1200){
            this.price += (this.velocit-100) * (50/100)
        }

        if(this.maxWith>7){
            this.price *= 3/100
        }
    }
}