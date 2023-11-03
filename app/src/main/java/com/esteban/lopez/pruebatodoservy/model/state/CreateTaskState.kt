package com.esteban.lopez.pruebatodoservy.model.state

import com.esteban.lopez.pruebatodoservy.model.model.db.Task

data class CreateTaskState(
    val tasks: List<Task> = listOf(),
)

sealed class CreateTaskSideEffect {
    data class Toast(val text: String) : CreateTaskSideEffect()
}