package com.loaizasoftware.usershandlerapp.domain.usecase

import androidx.annotation.Nullable
import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val repository: UserRepository): UseCase<Nullable, Flow<List<User>>>() {

    override suspend fun run(params: Nullable): Flow<List<User>> {
        return repository.getAllUsers()
    }

}