package com.loaizasoftware.usershandlerapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.loaizasoftware.usershandlerapp.data.local.dao.UserDao
import com.loaizasoftware.usershandlerapp.data.local.database.migrations.MIGRATION_2_3
import com.loaizasoftware.usershandlerapp.domain.model.User

@Database(entities = [User::class], version = 3) //Crucially, set the version to your current
// SQLiteOpenHelper schema version. This is vital for Room to correctly handle the existing database.
abstract class AppDatabase: RoomDatabase() {

    //Add all the DAOs here
    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my_database.db" // <<< MUST MATCH YOUR EXISTING DB NAME USED WITH SQLite
                )
                    .addMigrations(MIGRATION_2_3) // Add future migrations here
                    // .fallbackToDestructiveMigration() // NOT for initial migration from SQLite
                .build()
                INSTANCE = instance
                instance
            }
        }

    }

}