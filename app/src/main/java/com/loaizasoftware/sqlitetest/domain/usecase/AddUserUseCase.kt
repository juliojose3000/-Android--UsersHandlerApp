package com.loaizasoftware.sqlitetest.domain.usecase

import com.loaizasoftware.sqlitetest.domain.model.User
import com.loaizasoftware.sqlitetest.domain.repositories.UserRepository

class AddUserUseCase(private val repository: UserRepository): UseCase<User, Boolean>() {

    override fun run(params: User): Boolean {
        return repository.addUser(params)
    }

}