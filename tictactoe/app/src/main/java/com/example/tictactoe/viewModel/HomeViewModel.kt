package com.example.tictactoe.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val multiplayerMutableLiveData = MutableLiveData<Boolean>() //esto es para que el viewModel tenga info del estado del boton multijugador lo metemos en un una especie de lista en la cual vamos a indicarle los posibles estados que puede tener en este caso un botón y esto ahora se le añadiria al boton del home fragment

    val multiplayerLiveData : LiveData<Boolean> = multiplayerMutableLiveData //Este es el Live data que va a ver el boton y solo los puede ver y no emitir datos

    //El fragment es tonto solo sirve para mostrar datos no sabe su estado eso lo calcula el viewmodel

    fun getMultiplayerState(){ //Con esta funcion es para que el fragment pregunte como esta el boton

        viewModelScope.launch(Dispatchers.IO) {
            multiplayerMutableLiveData.postValue(false) //Cuando salga al hilo principal ponga ese valor
            delay(4000) //Delay de 4 segundos para toda la corrutina
            multiplayerMutableLiveData.postValue(true) //aue cuando salga al hilo principal ponga este valor
        }//crear una corrutina , el primer argunemto dice donde se ejecuta, en IO es el background
    }
}