package com.loaizasoftware.sqlitetest.domain.usecase

abstract class UseCase<in K, out T> {
    abstract fun run(params: K): T
}