package com.esteban.lopez.pruebatodoservy.model.model.db.type_converters

import androidx.room.TypeConverter
import com.esteban.lopez.pruebatodoservy.model.model.TaskColor

class TaskColorConverter {

    @TypeConverter
    fun toStatus(value: String): TaskColor {
        return TaskColor.valueOf(value)
    }

    @TypeConverter
    fun fromStatus(status: TaskColor): String {
        return status.text
    }
}
