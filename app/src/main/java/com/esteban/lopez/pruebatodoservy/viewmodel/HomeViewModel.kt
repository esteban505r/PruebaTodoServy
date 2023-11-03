package com.esteban.lopez.pruebatodoservy.viewmodel

import androidx.lifecycle.ViewModel
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.repository.TasksRepository
import com.esteban.lopez.pruebatodoservy.model.state.HomeSideEffect
import com.esteban.lopez.pruebatodoservy.model.state.HomeState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription
import org.orbitmvi.orbit.viewmodel.container

class HomeViewModel(private val repository: TasksRepository): ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    fun getAll() = intent {
        val response = repository.getAll()
        repeatOnSubscription {
            response.collect { tasks ->
                reduce {
                    state.copy(tasks = tasks)
                }
            }
        }
    }

    fun deleteTask(id: Int) = intent {
        repository.delete(id)
        postSideEffect(HomeSideEffect.Toast("Task deleted"))
    }

    fun updateTask(task: Task) = intent {
        repository.update(task)
        postSideEffect(HomeSideEffect.Toast("Task updated"))
    }

    fun createTask(task:Task) = intent {
        repository.create(task)
        postSideEffect(HomeSideEffect.Toast("Task inserted"))
    }
}