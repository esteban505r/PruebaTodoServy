package com.esteban.lopez.pruebatodoservy.model.model.db.type_converters

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeConverter {

    private val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    @TypeConverter
    fun fromLocalTime(localTime: LocalTime?): String? {
        return localTime?.format(formatter)
    }

    @TypeConverter
    fun toLocalTime(localTimeString: String?): LocalTime? {
        return localTimeString?.let {
            return try {
                LocalTime.parse(it, formatter)
            } catch (e: Exception) {
                null
            }
        }
    }
}