package com.loaizasoftware.sqlitetest.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.loaizasoftware.sqlitetest.domain.model.User
import com.loaizasoftware.sqlitetest.domain.model.createUser
import com.loaizasoftware.sqlitetest.domain.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel (private val repository: UserRepository): ViewModel() {

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

    fun addUser(): Boolean {

        val user = createUser {
            this.name = _nameState.value
            this.age = _ageState.value
        }

        setName("")
        setAge(0)

        return repository.addUser(user).apply {
            listUsers.value += user
        }

    }

}