package com.loaizasoftware.usershandlerapp.domain.usecase

import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository

class AddUserUseCase(private val repository: UserRepository): UseCase<User, Unit>() {

    override suspend fun run(params: User) {
        repository.addUser(params)
    }

}