package com.loaizasoftware.sqlitetest.domain.usecase

import androidx.annotation.Nullable
import com.loaizasoftware.sqlitetest.domain.model.User
import com.loaizasoftware.sqlitetest.domain.repositories.UserRepository

class GetAllUsersUseCase(private val repository: UserRepository): UseCase<Nullable, List<User>>() {

    override fun run(params: Nullable): List<User> {
        return repository.getAllUsers()
    }

}