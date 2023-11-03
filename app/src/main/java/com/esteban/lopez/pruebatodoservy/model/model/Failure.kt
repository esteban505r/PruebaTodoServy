package com.esteban.lopez.pruebatodoservy.model.model

sealed class Failure {

    data class Error(val message: String,val stackTrace:String) : Failure()
    data object NetworkConnection : Failure()
    data object ServerError : Failure()
    data object NotFound : Failure()
    data object AccessDenied : Failure()
    data object UnknownError : Failure()
    data object NoData : Failure()
    abstract class FeatureFailure : Failure()
}