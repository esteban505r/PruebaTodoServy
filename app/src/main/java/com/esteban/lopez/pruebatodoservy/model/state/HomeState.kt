package com.esteban.lopez.pruebatodoservy.model.state

import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus
import java.time.LocalTime

data class HomeState(
    val tasks: List<Task> = listOf(),
    val filterBy:TaskStatus? = null,
    val localTime: LocalTime = LocalTime.now()
)

sealed class HomeSideEffect {
    data class Toast(val text: String) : HomeSideEffect()
    data class NavigateToDetail(val task: Task) : HomeSideEffect()
}