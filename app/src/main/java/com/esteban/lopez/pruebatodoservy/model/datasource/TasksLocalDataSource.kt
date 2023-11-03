package com.esteban.lopez.pruebatodoservy.model.datasource

import com.esteban.lopez.pruebatodoservy.model.model.Failure
import arrow.core.Either
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus
import com.esteban.lopez.pruebatodoservy.model.model.db.dao.TaskDao
import kotlinx.coroutines.flow.Flow

class TasksLocalDataSource(private val dao:TaskDao){

    fun getAllByStatus(status:TaskStatus?): Either<Failure,Flow<List<Task>>> {
        return try {
            if(status!=null) {
                Either.Right(dao.getAllTasksByStatus(status))
            }
            else{
               Either.Right(dao.getAllTasks())
            }
        } catch(e:Exception){
            Either.Left(Failure.UnknownError)
        }
    }

    suspend fun getDetail(id:Long): Either<Failure,Task> {
        //This method returns the first element of the result set.
        return try{
            val result = dao.getTaskById(id)
            if(result!=null){
                Either.Right(result)
            }
            else {
                Either.Left(Failure.NotFound)
            }
        }
        catch(e:Exception){
            Either.Left(Failure.Error(e.toString(),e.stackTraceToString()))
        }
    }

    suspend fun create(task: Task): Either<Failure,Long> {
        //This method returns the new rowId for the inserted item.
        return try{
            val result = dao.insert(task)
            if(result!=null){
                Either.Right(result)
            }
            else {
                Either.Left(Failure.UnknownError)
            }
        }
        catch(e:Exception){
            Either.Left(Failure.Error(e.toString(),e.stackTraceToString()))
        }
    }

    suspend fun update(task:Task):Either<Failure,Int> {
        //This method returns the number of rows affected by this query.
       return try{
           val result = dao.update(task)
           if(result>0){
               Either.Right(result)
           }
           else {
               Either.Left(Failure.NotFound)
           }
       }
       catch(e:Exception){
           Either.Left(Failure.Error(e.toString(),e.stackTraceToString()))
       }
    }

    suspend fun delete(id: Long):Either<Failure,Int>  {
        //This method returns the number of rows affected by this query.
        return try{
            val result = dao.delete(id)
            if(result>0){
                Either.Right(result)
            }
            else {
                Either.Left(Failure.NotFound)
            }
        }
        catch(e:Exception){
            Either.Left(Failure.Error(e.toString(),e.stackTraceToString()))
        }
    }
}