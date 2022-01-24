package es.fesac.tictactoe.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import es.fesac.tictactoe.R
import es.fesac.tictactoe.common.USER_COLLECTION
import es.fesac.tictactoe.common.USER_FIELD_USER
import es.fesac.tictactoe.extension.isEmail
import es.fesac.tictactoe.model.UserBo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    private val loadingMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    private val errorMutableLiveData by lazy {
        MutableLiveData<String?>()
    }
    private val successLoginMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    fun loadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun errorLiveData(): LiveData<String?> = errorMutableLiveData
    fun successLoginLiveData(): LiveData<Boolean> = successLoginMutableLiveData

    fun login(context: Context, user: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = true
            }

            var error = validationFields(context, user, password)
            if (error == null) {
                error = try {
                    if (user.isEmail()) {
                        Firebase.auth.signInWithEmailAndPassword(user, password).await()
                        null
                    } else {
                        val snapshot = Firebase.firestore
                            .collection(USER_COLLECTION)
                            .whereEqualTo(USER_FIELD_USER, user)
                            .get()
                            .await()
                        if (snapshot.documents.isEmpty()) {
                            context.getString(R.string.login__error_user_not_exist)
                        } else {
                            val document = snapshot.documents.firstOrNull()
                            if (document != null) {
                                val userBo = document.toObject(UserBo::class.java)
                                if (userBo != null) {
                                    Firebase.auth.signInWithEmailAndPassword(userBo.email, password).await()
                                    null
                                } else {
                                    context.getString(R.string.login__error_user_not_exist)
                                }
                            } else {
                                context.getString(R.string.login__error_user_not_exist)
                            }
                        }
                    }
                } catch (exception: Exception) {
                    exception.localizedMessage
                }
            }
            val loginSuccess = error == null

            withContext(Dispatchers.Main) {
                loadingMutableLiveData.value = false
                successLoginMutableLiveData.value = loginSuccess
                errorMutableLiveData.value = error
            }
        }
    }

    private fun validationFields(context: Context, email: String, password: String): String? {
        val errorResId = when {
            email.isBlank() -> R.string.register_error__empty_email
            password.isBlank() -> R.string.error_loading__empty_password
            else -> null
        }
        return errorResId?.let {
            context.getString(errorResId)
        }
    }
}