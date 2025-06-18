package com.loaizasoftware.usershandlerapp.domain.usecase

import androidx.annotation.Nullable
import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository

class GetAllUsersUseCase(private val repository: UserRepository): UseCase<Nullable, List<User>>() {

    override fun run(params: Nullable): List<User> {
        return repository.getAllUsers()
    }

}