package com.esteban.lopez.pruebatodoservy.model.repository;

import arrow.core.Either
import com.esteban.lopez.pruebatodoservy.model.datasource.TasksLocalDataSource
import com.esteban.lopez.pruebatodoservy.model.model.Failure
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
import java.time.LocalDate
import java.util.Date


class TasksRepository(
    private val localDataSource: TasksLocalDataSource,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllByStatus(status:TaskStatus?): Either<Failure,Flow<List<Task>>> {
       return localDataSource.getAllByStatus(status).map {
           /*if the task is not pending but when it was updated it was before today,
            then pending is returned */
           it.transformLatest {tasks->
                emit(tasks.map { task ->
                    task.copy(status =
                    if(task.updatedAt.isBefore(LocalDate.now()))
                        TaskStatus.PENDING
                    else
                        task.status)
                })
           }
       };
    }

    suspend fun getById(id:Long): Either<Failure,Task> {
        return localDataSource.getDetail(id);
    }

    suspend fun update(task:Task): Either<Failure,Int>{
        return localDataSource.update(task.copy(updatedAt = LocalDate.now()));
    }

    suspend fun create(task: Task): Either<Failure,Long> {
        return localDataSource.create(task);
    }

    suspend fun delete(taskId: Long): Either<Failure,Int>{
        return localDataSource.delete(taskId);
    }

}