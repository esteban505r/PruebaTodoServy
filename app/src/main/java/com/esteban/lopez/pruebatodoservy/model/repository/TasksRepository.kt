package com.esteban.lopez.pruebatodoservy.model.repository;

import arrow.core.Either
import com.esteban.lopez.pruebatodoservy.model.datasource.TasksLocalDataSource
import com.esteban.lopez.pruebatodoservy.model.model.Failure
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus
import kotlinx.coroutines.flow.Flow


class TasksRepository(
    private val localDataSource: TasksLocalDataSource,
) {

    fun getAllByStatus(status:TaskStatus?): Either<Failure,Flow<List<Task>>> {
       return localDataSource.getAllByStatus(status);
    }

    suspend fun getById(id:Long): Either<Failure,Task> {
        return localDataSource.getDetail(id);
    }

    suspend fun update(task:Task): Either<Failure,Int>{
        return localDataSource.update(task);
    }

//    suspend fun getDetail(id:Int): Either<TaskNotFound,Flow<Task>> {
//        return localDataSource.getDetail(id);
//    }
//
    suspend fun create(task: Task): Either<Failure,Long> {
        return localDataSource.create(task);
    }
//
//    suspend fun update(task:Task): Either<TaskNotUpdated,Int>{
//        return localDataSource.update(task);
//    }
//
    suspend fun delete(taskId: Long): Either<Failure,Int>{
        return localDataSource.delete(taskId);
    }

}