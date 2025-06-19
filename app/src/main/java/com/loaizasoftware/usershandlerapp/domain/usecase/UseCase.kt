package com.loaizasoftware.usershandlerapp.domain.usecase

abstract class UseCase<in K, out T> {
    abstract suspend fun run(params: K): T
}