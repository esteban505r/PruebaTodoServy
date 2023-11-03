package com.esteban.lopez.pruebatodoservy.model.model.db.type_converters

import androidx.room.TypeConverter
import com.esteban.lopez.pruebatodoservy.model.model.TaskIcon

class TaskIconConverter {

    @TypeConverter
    fun toStatus(value: String): TaskIcon {
        return TaskIcon.valueOf(value)
    }

    @TypeConverter
    fun fromStatus(status: TaskIcon): String {
        return status.name
    }
}
