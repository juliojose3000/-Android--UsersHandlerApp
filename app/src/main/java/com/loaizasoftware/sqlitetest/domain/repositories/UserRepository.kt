package com.loaizasoftware.sqlitetest.domain.repositories

import com.loaizasoftware.sqlitetest.domain.model.User

interface UserRepository {

    fun addUser(user: User): Boolean

    fun getAllUsers(): List<User>

}