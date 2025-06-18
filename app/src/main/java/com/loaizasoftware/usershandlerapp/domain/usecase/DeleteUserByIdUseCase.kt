package com.loaizasoftware.usershandlerapp.domain.usecase

import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository

class DeleteUserByIdUseCase(private val repository: UserRepository): UseCase<Int, Boolean>() {

    override fun run(params: Int): Boolean {
        return repository.deleteUserById(params)
    }

}