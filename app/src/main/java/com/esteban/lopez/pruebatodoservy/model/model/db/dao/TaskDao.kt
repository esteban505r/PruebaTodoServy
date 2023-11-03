package com.esteban.lopez.pruebatodoservy.model.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long?

    @Update
    suspend fun update(task: Task):Int

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun delete(id: Long):Int

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()

    @Query("SELECT * FROM tasks WHERE status = :status")
    fun getAllTasksByStatus(status: TaskStatus): Flow<List<Task>>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): Task?

}
