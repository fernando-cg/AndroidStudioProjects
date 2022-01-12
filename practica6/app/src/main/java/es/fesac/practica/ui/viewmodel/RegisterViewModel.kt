package es.fesac.practica.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fesac.practica.R
import es.fesac.practica.common.MyApplication
import es.fesac.practica.data.manager.RepositoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * TODO 6.2:
 * Implementar el registro, para ello se deberá:
 * - Crear 3 LiveData para controlar, loading, registro correcto y error. En caso de que el registro sea correcto, se navegará a la home.
 * - Validaciones de datos:
 *      * Email vacío   register_error__empty_email
 *      * Password vacía  register_error__empty_password
 *      * Las contraseñas no coinciden  register_error__password_repeat
 *      * Usuario vacío  register_error__empty_user
 *      * Check no marcado  register_error__accept_term_and_conditions
 */
class RegisterViewModel : ViewModel() {
    private val loadingMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val registerMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val errorMutableLiveData: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    fun getLoadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun getRegisterLiveData(): LiveData<Boolean> = registerMutableLiveData
    fun getErrorLiveData(): LiveData<String?> = errorMutableLiveData
    /**
     * TODO 6.2 Corrutina:
     * En el método register se deberá implementar una corrutina de la siguiente forma:
     *   1. Cuando se empiece la corrutina se pondrá el loading a visible.
     *   2. Se validarán los datos del método y en caso de que no se retorne ningún error se efectuará el registro.
     *   3. Si el registro no da ningún error, se notificará al fragment para que pueda navegar a la home, en caso contrario se notificará del error para que se pinte por pantalla.
     *   4. Se quitará la pantalla de carga.
     */
    fun register(email: String, password: String, passwordRepeat: String, user: String, check: Boolean) {
        // TODO 6.2
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }
            var error = validateFields(email,password,passwordRepeat,user, check)

            if(error==null){
                error = RepositoryManager.register(email= email, password= password)
            }
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                registerMutableLiveData.value = error == null
                errorMutableLiveData.value = error
            }
        }
    }

    private fun validateFields(email: String, password: String, passwordRepeat: String, user: String, check: Boolean): String?{
        return when{
            email.isBlank() ->{
                MyApplication.instance.getString(R.string.register_error__empty_email)
            }
            password.isBlank() ->{
                MyApplication.instance.getString(R.string.register_error__empty_password)
            }
            user.isBlank()->{
                MyApplication.instance.getString(R.string.register_error__empty_user)
            }
            passwordRepeat.isBlank() ->{
                MyApplication.instance.getString(R.string.register_error__password_repeat)
            }
            !check ->{
                MyApplication.instance.getString(R.string.register_error__accept_term_and_conditions)
            }
            else ->{
                null
            }
        }
    }
}