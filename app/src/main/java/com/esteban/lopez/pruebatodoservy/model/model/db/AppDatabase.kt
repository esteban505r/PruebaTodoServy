package com.esteban.lopez.pruebatodoservy.model.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esteban.lopez.pruebatodoservy.model.model.db.dao.TaskDao
import com.esteban.lopez.pruebatodoservy.model.model.db.type_converters.LocalTimeConverter
import com.esteban.lopez.pruebatodoservy.model.model.db.type_converters.TaskStatusConverter

@Database(entities = [Task::class], version = 1)
@TypeConverters(TaskStatusConverter::class, LocalTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}