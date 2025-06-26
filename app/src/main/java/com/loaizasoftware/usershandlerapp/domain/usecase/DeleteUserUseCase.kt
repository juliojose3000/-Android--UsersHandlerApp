package com.loaizasoftware.usershandlerapp.domain.usecase

import com.loaizasoftware.usershandlerapp.domain.repositories.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UserRepository): UseCase<Int, Unit>() {

    override suspend fun run(params: Int) {
        repository.setUserToDelete(params)
    }

}