package com.loaizasoftware.usershandlerapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "name")
    val name: String? = "",
    @ColumnInfo(name = "age") // If column name matches field name, @ColumnInfo is optional
    val age: Int? = 0,
    @ColumnInfo(name = "isSynced")
    val synced: Boolean = false, //When a new user is created, the isSynced property will be false because we need to indicate that this is a new user and it should be added to the remote db
    @ColumnInfo(name = "syncAction")
    val syncAction: Int = SyncActionEnum.ADD.value //By default, when a new users is created, we set the sync action as [SyncActionEnum.ADD] to eventually add it to the remote database
) {

    class Builder {
        var id: Int? = null
        var name: String = ""
        var age: Int = 0
        var isSynced: Boolean = false
        var syncAction: Int = SyncActionEnum.ADD.value

        fun build() = User(
            id = id,
            name = name,
            age = age,
            synced = isSynced,
            syncAction = syncAction
        )

    }

}

//Top-level function to create a User object
//fun user(block: User.Builder.() -> Unit): User = User.Builder().apply(block).build()

fun createUser(block: User.Builder.() -> Unit): User {
    val builder = User.Builder()
    block(builder)
    return builder.build()
}