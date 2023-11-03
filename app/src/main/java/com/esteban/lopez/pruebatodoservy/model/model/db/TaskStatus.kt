package com.esteban.lopez.pruebatodoservy.model.model.db

enum class TaskStatus(val status: String,val text:String) {
    PENDING("PENDING","Pendiente"),
    IN_PROGRESS("IN_PROGRESS","En progreso"),
    COMPLETED("COMPLETED","Completada"),
    CANCELLED("CANCELLED","Cancelado")
}
