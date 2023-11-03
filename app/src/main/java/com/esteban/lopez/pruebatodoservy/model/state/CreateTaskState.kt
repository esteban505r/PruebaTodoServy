package com.esteban.lopez.pruebatodoservy.model.state

import com.esteban.lopez.pruebatodoservy.model.model.db.Task

data class CreateTaskState(
    val task: Task? = null,
)

sealed class CreateTaskSideEffect {
    data class Toast(val text: String) : CreateTaskSideEffect()

    data object Popup: CreateTaskSideEffect()
}