package com.esteban.lopez.pruebatodoservy.model.model.db.type_converters

import androidx.room.TypeConverter
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus

class TaskStatusConverter {

    @TypeConverter
    fun toStatus(value: String): TaskStatus {
        return TaskStatus.valueOf(value)
    }

    @TypeConverter
    fun fromStatus(status: TaskStatus): String {
        return status.status
    }
}
