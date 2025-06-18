package com.loaizasoftware.sqlitetest.domain.usecase

import com.loaizasoftware.sqlitetest.domain.repositories.UserRepository

class DeleteUserByIdUseCase(private val repository: UserRepository): UseCase<Int, Boolean>() {

    override fun run(params: Int): Boolean {
        return repository.deleteUserById(params)
    }

}