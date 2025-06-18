package com.loaizasoftware.usershandlerapp.presentation.viewmodels

import androidx.annotation.Nullable
import androidx.lifecycle.ViewModel
import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.model.createUser
import com.loaizasoftware.usershandlerapp.domain.usecase.AddUserUseCase
import com.loaizasoftware.usershandlerapp.domain.usecase.DeleteUserByIdUseCase
import com.loaizasoftware.usershandlerapp.domain.usecase.GetAllUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel (
    private val addUserUseCase: AddUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserByIdUseCase: DeleteUserByIdUseCase
): ViewModel() {

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    private val _ageState = MutableStateFlow(0)
    val ageState: StateFlow<Int> = _ageState

    val listUsers = MutableStateFlow(listOf<User>())

    fun setName(name: String) {
        _nameState.value = name
    }

    fun setAge(age: Int) {
        _ageState.value = age
    }

    init {
        getAllUsers()
    }

    private fun getAllUsers() {

        listUsers.value = getAllUsersUseCase.run(Nullable())

    }

    fun addUser(): Boolean {

        val user = createUser {
            this.name = _nameState.value
            this.age = _ageState.value
        }

        setName("")
        setAge(0)

        addUserUseCase
            .run(user)
            .also { if (it) { listUsers.value += user } }

        return true

    }


    fun deleteUserById(userId: Int): Boolean {

        return deleteUserByIdUseCase.run(userId).also {

            listUsers.value = listUsers.value.filter { it.id != userId }

        }

    }


}