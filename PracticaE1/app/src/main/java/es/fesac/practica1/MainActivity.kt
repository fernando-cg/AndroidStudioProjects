package es.fesac.practica1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import es.fesac.practica1.model.Client
import es.fesac.practica1.model.Device
import es.fesac.practica1.model.Seller
import es.fesac.practica1.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // NO TOCAR NADA DE ESTE CÓDIGO
        val personList = generateClientAndSellerList()
        val deviceList = generateMobileAndTabletList()
        val orderList = generateOrderList()

        // NO TOCAR NADA DE ESTE CÓDIGO
        updateClientPoints(personList.filterIsInstance<Client>(), orderList)

        // NO TOCAR NADA DE ESTE CÓDIGO
        val deviceMapToSell = mapOf(
            deviceList.random() to 2,
            deviceList.random() to 1,
            deviceList.random() to 3
        )
        // NO TOCAR NADA DE ESTE CÓDIGO
        val newOrder = personList.filterIsInstance<Seller>().random().sell(deviceMapToSell, personList.filterIsInstance<Client>().first())
        orderList.add(newOrder)

        // NO TOCAR NADA DE ESTE CÓDIGO
        val bestSeller = bestSeller(personList.filterIsInstance<Seller>(), orderList)

        // NO TOCAR NADA DE ESTE CÓDIGO
        var retorno = orderClientByAccumulatedPoints(personList.filterIsInstance<Client>())

    }
}