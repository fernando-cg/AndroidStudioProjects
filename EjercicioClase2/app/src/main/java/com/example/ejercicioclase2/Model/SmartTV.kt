package com.example.ejercicioclase2.Model

class SmartTV (sku: Int,price: Float,weight: Float,var android: Boolean,var size: Int): Appliance(sku,price,weight){
    fun priceTV(){
        if(this.size>40){
            price += ((this.size-40)*10)
        }

        if(this.android){
            this.price *= 5/100
        }
    }
}