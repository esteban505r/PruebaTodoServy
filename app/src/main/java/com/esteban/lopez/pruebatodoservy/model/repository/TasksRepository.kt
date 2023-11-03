package com.esteban.lopez.pruebatodoservy.model.repository;

import com.esteban.lopez.pruebatodoservy.model.datasource.TasksLocalDataSource
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import kotlinx.coroutines.flow.Flow


class TasksRepository(
    private val localDataSource: TasksLocalDataSource,
) {

    suspend fun getAll(): Flow<List<Task>> {
        return localDataSource.getAll();
    }

    suspend fun getDetail(id:Int): Flow<Task> {
        return localDataSource.getDetail(id);
    }

    suspend fun create(task: Task): Long {
        return localDataSource.create(task);
    }

    suspend fun update(task:Task){
        return localDataSource.update(task);
    }

    suspend fun delete(taskId: Int) {
        return localDataSource.delete(taskId);
    }

}