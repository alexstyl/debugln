package com.alexstyl.debugln

var allowLogging: Boolean = true

private const val COLOR_RED = "\u001b[31m"
private const val COLOR_AQUA = "\u001b[36m"
private const val COLOR_LIGHT_GRAY = "\u001b[37m"
private const val COLOR_ORANGE = "\u001b[38;2;255;165;0m"
private const val COLOR_RESET = "\u001b[0m"

fun debugln(message: () -> String) {
    if (allowLogging) {
        println(message())
    }
}

fun verboseln(message: () -> String) {
    if (allowLogging) {
        println(message(), COLOR_LIGHT_GRAY)
    }
}


fun infoln(message: () -> String) {
    if (allowLogging) {
        println(message(), COLOR_AQUA)
    }
}

fun warnln(message: () -> String) {
    if (allowLogging) {
        println(message(), COLOR_ORANGE)
    }
}

fun errorln(message: () -> String) {
    if (allowLogging) {
        println(message(), COLOR_RED)
    }
}

private fun println(message: String, color: String) {
    println(color + message + COLOR_RESET)
}
