package com.esteban.lopez.pruebatodoservy.viewmodel

import androidx.lifecycle.ViewModel
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.repository.TasksRepository
import com.esteban.lopez.pruebatodoservy.model.state.CreateTaskSideEffect
import com.esteban.lopez.pruebatodoservy.model.state.CreateTaskState
import com.esteban.lopez.pruebatodoservy.model.state.HomeSideEffect
import com.esteban.lopez.pruebatodoservy.model.state.HomeState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription
import org.orbitmvi.orbit.viewmodel.container

class CreateTaskViewModel(private val repository: TasksRepository):
    ContainerHost<CreateTaskState, CreateTaskSideEffect>, ViewModel() {

    override val container = container<CreateTaskState, CreateTaskSideEffect>(CreateTaskState())

    suspend fun createTask(task:Task) = intent {
        repository.create(task)
        postSideEffect(CreateTaskSideEffect.Toast("Task inserted"))
    }
}