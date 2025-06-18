package com.loaizasoftware.usershandlerapp.domain.repositories

import com.loaizasoftware.usershandlerapp.domain.model.User

interface UserRepository {

    fun addUser(user: User): Boolean

    fun getAllUsers(): List<User>

    fun deleteUserById(userId: Int): Boolean

}