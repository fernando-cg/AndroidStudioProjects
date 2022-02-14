package es.fesac.practica.ui.extension

import android.content.Context
import es.fesac.practica.common.PREFERENCES_FILE_NAME
import es.fesac.practica.common.PREFERENCE_KEY_PREFIX
import java.util.*

fun Context.saveData(key: String, value: Long) {
    val sharedPref = this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putLong("${PREFERENCE_KEY_PREFIX}_${key.uppercase(Locale.ROOT)}", value)
        apply()
    }
}

fun Context.getData(key: String, defaultValue: Long = 0): Long {
    val sharedPref = this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    return sharedPref.getLong("${PREFERENCE_KEY_PREFIX}_${key.uppercase(Locale.ROOT)}", defaultValue)
}

fun Context.saveDataByKeyInt(key: String, value: Int?) {
    val sharedPref = this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putInt(key.uppercase(Locale.ROOT), value ?: 0)
        apply()
    }
}

fun Context.saveDataByKeyLong(key: String, value: Long?) {
    val sharedPref = this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putLong(key.uppercase(Locale.ROOT), value ?: 0L)
        apply()
    }
}

fun Context.saveDataByKeyBoolean(key: String, value: Boolean?) {
    val sharedPref = this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putBoolean(key.uppercase(Locale.ROOT), value ?: false)
        apply()
    }
}

fun Context.getIntDataByKey(key: String, defaultValue: Int = 0): Int {
    val sharedPref = this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    return sharedPref.getInt(key.uppercase(Locale.ROOT), defaultValue)
}

fun Context.getLongDataByKey(key: String, defaultValue: Long = 0L): Long {
    val sharedPref = this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    return sharedPref.getLong(key.uppercase(Locale.ROOT), defaultValue)
}

fun Context.getBooleanDataByKey(key: String, defaultValue: Boolean = false): Boolean {
    val sharedPref = this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(key.uppercase(Locale.ROOT), defaultValue)
}