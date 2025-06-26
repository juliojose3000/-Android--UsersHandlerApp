package com.loaizasoftware.usershandlerapp.domain.model


/**
 * Represents the type of synchronization action to be performed on a user entity.
 *
 * This enum is used to indicate whether a user should be added, updated, or deleted
 * during the synchronization process with a remote database such as Firebase.
 *
 * @property value The integer representation of the sync action, used for storage in Room.
 *
 * - [ADD] (0): The user is new and needs to be added remotely.
 * - [UPDATE] (1): The user has been modified and should be updated remotely.
 * - [DELETE] (2): The user has been marked for deletion and should be removed remotely.
 */
enum class SyncActionEnum(val value: Int) {
    ADD(0),
    UPDATE(1),
    DELETE(2)
}
