package com.esteban.lopez.pruebatodoservy.model.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.reduce
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.timer

private val formatter = DateTimeFormatter.ofPattern("hh:mm a")

fun LocalTime.toFormattedString(): String {
    return format(formatter)
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toLocalTime():LocalTime =
    LocalTime.of(this.hour, this.minute)

fun LocalTime.timeLeft(localTime: LocalTime): String{
    if (this > localTime) {
        val duration = Duration.between(localTime,this)
        val hours = duration.toHours()
        val minutes = duration.minusHours(hours).toMinutes()
        val seconds = duration.minusHours(hours).minusMinutes(minutes).seconds
        return "${hours}h ${minutes}m ${seconds}s"
    }
    return this.toString()

}