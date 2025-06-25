package com.loaizasoftware.usershandlerapp.domain.usecase

import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncDataUseCase @Inject constructor(private val userRepository: UserRepository): UseCase<Unit, Flow<List<User>>>() {

    override suspend fun run(params: Unit): Flow<List<User>> {

        return userRepository.getAllUnsyncedUsers()

    }

}