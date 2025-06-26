package com.loaizasoftware.usershandlerapp.data.local.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add the new column with a default value (0 = false)
        database.execSQL("ALTER TABLE users ADD COLUMN isSynced INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add the new column with a default value (0 = false)
        database.execSQL("ALTER TABLE users ADD COLUMN syncAction INTEGER NOT NULL DEFAULT 0")
    }
}