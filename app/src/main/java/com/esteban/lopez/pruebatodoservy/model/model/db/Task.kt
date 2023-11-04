package com.esteban.lopez.pruebatodoservy.model.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esteban.lopez.pruebatodoservy.model.model.TaskColor
import com.esteban.lopez.pruebatodoservy.model.model.TaskIcon
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "task_name")
    val name: String,

    @ColumnInfo(name = "task_description")
    val description: String,

    @ColumnInfo(name = "end_time")
    val endTime: LocalTime,

    @ColumnInfo(name = "status")
    val status: TaskStatus,

    @ColumnInfo(name = "icon")
    val icon: TaskIcon,

    @ColumnInfo(name = "color")
    val color: TaskColor,

    @ColumnInfo(name = "time")
    val time: LocalTime,

    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDate = LocalDate.now(),
)
