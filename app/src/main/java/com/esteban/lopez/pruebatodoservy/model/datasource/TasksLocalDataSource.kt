package com.esteban.lopez.pruebatodoservy.model.datasource

import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.model.db.dao.TaskDao
import kotlinx.coroutines.flow.Flow

class TasksLocalDataSource(private val dao:TaskDao){

    fun getAll(): Flow<List<Task>> {
        return dao.getAllTasks()
    }

    fun getDetail(id:Int): Flow<Task> {
        return  dao.getTaskById(id)
    }

    suspend fun create(task: Task): Long {
        return dao.insert(task)
    }

    suspend fun update(task:Task){
        return dao.update(task)
    }

    suspend fun delete(id: Int) {
        return dao.delete(id)
    }
}