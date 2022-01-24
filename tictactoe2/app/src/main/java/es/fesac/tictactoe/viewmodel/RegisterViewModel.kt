package es.fesac.tictactoe.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.fesac.tictactoe.R
import es.fesac.tictactoe.model.UserBo
import es.fesac.tictactoe.model.toMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {
    private val loadingMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    private val errorMutableLiveData by lazy {
        MutableLiveData<String?>()
    }
    private val successRegisterMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    fun loadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun errorLiveData(): LiveData<String?> = errorMutableLiveData
    fun successRegisterLiveData(): LiveData<Boolean> = successRegisterMutableLiveData

    fun register(context:Context,user: String, email: String, password: String, check: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            val error = validationFields(context,user,email,password,check)
            if(error==null) {
                val error: String? = try {
                    val snapshot = Firebase.firestore
                        .collection("users")
                        .whereEqualTo("user",user)
                        .get()
                        .await()
                    if(snapshot.documents.isEmpty()){
                        Firebase.auth.createUserWithEmailAndPassword(email,password).await()
                        Firebase.auth.currentUser?.let { userLogged ->
                            val userToSave = UserBo(
                                id = userLogged.uid,
                                email = email,
                                user = user
                            )
                            Firebase.firestore.collection("users")
                                .add(userToSave.toMap())
                                .await()
                            null
                        }
                    }else{
                        context.getString(R.string.register__error_user_in_use)
                    }
                } catch (exception: Exception) {
                    exception.localizedMessage
                }
            }
            val loginSuccess = error == null

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                successRegisterMutableLiveData.value = loginSuccess
                errorMutableLiveData.value = error
            }
        }
    }

    private fun validationFields(
        context: Context,
        user: String,
        email: String,
        password: String,
        check: Boolean
    ): String? {
       val errorResId = when {
           user.isBlank() -> R.string.error_loading__empty_user

           email.isBlank() -> R.string.register_error__empty_email

           password.isBlank() -> R.string.register_error__empty_password

           check.not() -> R.string.register_error__accept_term_and_conditions

           else -> null

       }

        return errorResId?.let {
            context.getString(errorResId)
        }

    }
}