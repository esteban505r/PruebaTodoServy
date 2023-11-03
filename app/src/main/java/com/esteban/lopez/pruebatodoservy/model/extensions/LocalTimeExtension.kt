package com.esteban.lopez.pruebatodoservy.model.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("hh:mm a")

fun LocalTime.toFormattedString(): String {
    return format(formatter)
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toLocalTime():LocalTime =
    LocalTime.of(this.hour, this.minute)