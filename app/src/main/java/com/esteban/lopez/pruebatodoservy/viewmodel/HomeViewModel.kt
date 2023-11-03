package com.esteban.lopez.pruebatodoservy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus
import com.esteban.lopez.pruebatodoservy.model.repository.TasksRepository
import com.esteban.lopez.pruebatodoservy.model.state.HomeSideEffect
import com.esteban.lopez.pruebatodoservy.model.state.HomeState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalTime
import java.util.Timer
import kotlin.concurrent.timer

class HomeViewModel(private val repository: TasksRepository): ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    private var flowJob: Job? = null

    private var timer : Timer? = null

    //Timer to update time left in Tasks
    fun startTimer() = intent {
        timer = timer(period = 1000L) {
            viewModelScope.launch {
                reduce {
                    state.copy(localTime = LocalTime.now())
                }
            }
        }
    }

    override fun onCleared() {
        timer?.cancel()
        super.onCleared()
    }

    fun getAllByStatus(status:TaskStatus?) = intent {
        val response = repository.getAllByStatus(status)
        response.fold(
            {
                reduce {
                    state.copy(tasks = emptyList())
                }
                postSideEffect(HomeSideEffect.Toast("Error getting tasks"))
            },
            {
                flowJob?.cancel()
                flowJob = viewModelScope.launch {
                    it.collect{
                    reduce {
                        state.copy(tasks = it,filterBy = status)
                    }
                }
                }

            }
        )

    }


    suspend fun deleteTask(id: Long) = intent {
        val response = repository.delete(id)
        response.fold(
            {
                postSideEffect(HomeSideEffect.Toast("Task not found"))
            },
            {
                postSideEffect(HomeSideEffect.Toast("Task deleted"))
            })
        postSideEffect(HomeSideEffect.Toast("Task deleted"))
    }

    suspend fun updateTask(task: Task) = intent {
        val response = repository.update(task)
        response.fold(
            {
                postSideEffect(HomeSideEffect.Toast("Task not found"))
            },
            {
                postSideEffect(HomeSideEffect.Toast("Task updated"))
            })
    }

}