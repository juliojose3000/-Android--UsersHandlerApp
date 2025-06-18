package com.loaizasoftware.sqlitetest.domain.model

data class User(val id: Int, val name: String, val age: Int) {

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