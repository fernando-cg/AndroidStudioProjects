package es.fesac.tictactoe.extension

import android.util.Patterns

fun String.isEmail(): Boolean {
    return this.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.capitalize(): String {
    return if (this.isEmpty().not()) {
        this[0].toString().uppercase() + this.substring(1)
    } else {
        this
    }
}

/**
 * Pepe es guapo -> Pepe Es Guapo
 */
fun String.wordCapitalize(): String {
    val separator = " "
    val wordList = this.split(separator)
    for (word in wordList) {
        word.capitalize()
    }
    return wordList.joinToString(separator)
}