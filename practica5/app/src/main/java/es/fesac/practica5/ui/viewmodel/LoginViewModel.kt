package es.fesac.practica5.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * TODO 5.1:
 * Se pretende crear un ViewModel para la pantalla de Login, dicho ViewModel debe tener informar al fragment por tres LiveData:
 * Controla cuando se debe mostrar y ocultar la vista de carga.
 * Controla si el login es correcto o no, se debe:
 * Si el login es correcto se navegará a la pantalla de HomeFragment usando el método “goToHome()”.
 * Si el login no es correcto n ose hará nada.
 * Controla si se debe mostrar o no un mensaje de error. Se pueden dar varios casos:
 * El mensaje es nulo  No se mostrará nada.
 * El mensaje NO es nulo  Se mostrará un Toast usando el método “showToast()”.

 * TODO 5.2:
 * Implementar método login
 */
class LoginViewModel : ViewModel() {

    //show status loading view
    //check if i can make this with enum class
    private val statusLoadingViewMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getStatusLoadingViewLiveData(): LiveData<Boolean> = statusLoadingViewMutableLiveData

    //CheckLogin
    //check if i can make this with enum class

    private val checkLoginMutableLiveData : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getCheckLoginLiveData():LiveData<Boolean> = checkLoginMutableLiveData

    //error message

    private val errorMessageMutableLiveData:MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun getErrorMessageLiveData():LiveData<Int> = errorMessageMutableLiveData

    //Checking Function
    fun login(userFromInput: String, passwordFromInput: String) {
        //Dispatchers.IO Corrutine in backgorund thread
        //Dispatchers.Main corrutine in main thread
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                statusLoadingViewMutableLiveData.value = true
            }

            //Cambiar a enum class TODO
            if(userFromInput.isBlank()){
                if(passwordFromInput.isBlank()){
                    withContext(Dispatchers.Main) {
                        errorMessageMutableLiveData.value = 1
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        errorMessageMutableLiveData.value = 2
                    }
                }
            }else{
                if(passwordFromInput.isBlank()){
                    withContext(Dispatchers.Main) {
                        errorMessageMutableLiveData.value =  3
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        delay(2000)
                        statusLoadingViewMutableLiveData.value = false
                        errorMessageMutableLiveData.value = 0
                        checkLoginMutableLiveData.value = userFromInput == "admin" && passwordFromInput == "123456"
                    }
                }
            }

            delay(2000)
            withContext(Dispatchers.Main){
                statusLoadingViewMutableLiveData.value = false
            }
        }
    }

    //end this
}