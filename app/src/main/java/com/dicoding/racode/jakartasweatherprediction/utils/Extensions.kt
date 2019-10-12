package com.dicoding.racode.jakartasweatherprediction.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(format: String): String {
    var date = this
    date *= 1000
    val time = date
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)
    return sdf.format(Date(time))
}
