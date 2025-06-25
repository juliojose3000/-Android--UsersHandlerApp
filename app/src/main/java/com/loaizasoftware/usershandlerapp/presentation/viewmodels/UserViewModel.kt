package com.loaizasoftware.usershandlerapp.presentation.viewmodels

import androidx.annotation.Nullable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loaizasoftware.usershandlerapp.domain.model.User
import com.loaizasoftware.usershandlerapp.domain.model.createUser
import com.loaizasoftware.usershandlerapp.domain.usecase.AddUserUseCase
import com.loaizasoftware.usershandlerapp.domain.usecase.DeleteUserUseCase
import com.loaizasoftware.usershandlerapp.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel //It tells Hilt that the ViewModel can receive dependency injection.
class UserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
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

        //listUsers.value =

        CoroutineScope(Dispatchers.IO).launch {

            getAllUsersUseCase.run(Nullable())
                .collect {
                    listUsers.value = it
                }

        }

    }

    fun addUser() {

        val user = createUser {
            this.name = _nameState.value
            this.age = _ageState.value
        }

        setName("")
        setAge(0)

        viewModelScope.launch {

            addUserUseCase
                .run(user)
                .also { listUsers.value += user }

        }

    }


    fun deleteUserById(userId: Int) {

        viewModelScope.launch {

            val user = listUsers.value.find { it.id == userId }

            requireNotNull(user)

            deleteUserUseCase.run(user).also {

                listUsers.value = listUsers.value.filter { it.id != userId }

            }

        }

    }

}