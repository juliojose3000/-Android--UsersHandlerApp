package com.loaizasoftware.usershandlerapp.domain.usecase

import androidx.annotation.Nullable
import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase(private val repository: UserRepository): UseCase<Nullable, Flow<List<User>>>() {

    override suspend fun run(params: Nullable): Flow<List<User>> {
        return repository.getAllUsers()
    }

}