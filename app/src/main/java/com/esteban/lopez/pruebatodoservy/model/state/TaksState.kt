package com.esteban.lopez.pruebatodoservy.model.state

import com.esteban.lopez.pruebatodoservy.model.model.db.Task

data class HomeState(
    val tasks: List<Task> = listOf(),
)

sealed class HomeSideEffect {
    data class Toast(val text: String) : HomeSideEffect()
}