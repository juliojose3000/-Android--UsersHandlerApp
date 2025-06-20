package com.loaizasoftware.usershandlerapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "age") // If column name matches field name, @ColumnInfo is optional
    val age: Int?
) {

    class Builder {
        var id: Int = 0
        var name: String = ""
        var age: Int = 0

        fun build() = User(id = id, name = name, age = age)

    }

}

//Top-level function to create a User object
//fun user(block: User.Builder.() -> Unit): User = User.Builder().apply(block).build()

fun createUser(block: User.Builder.() -> Unit): User {
    val builder = User.Builder()
    block(builder)
    return builder.build()
}