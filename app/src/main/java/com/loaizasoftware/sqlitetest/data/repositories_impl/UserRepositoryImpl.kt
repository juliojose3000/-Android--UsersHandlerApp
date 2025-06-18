package com.loaizasoftware.sqlitetest.data.repositories_impl

import com.loaizasoftware.sqlitetest.data.database.MyDatabaseHelper
import com.loaizasoftware.sqlitetest.domain.model.User
import com.loaizasoftware.sqlitetest.domain.repositories.UserRepository

class UserRepositoryImpl(private val dbHelper: MyDatabaseHelper): UserRepository {

    override fun addUser(user: User): Boolean {
        return dbHelper.insertUser(user.name, user.age)
    }

    override fun getAllUsers(): List<User> {
        return dbHelper.getAllUsers()
    }

}