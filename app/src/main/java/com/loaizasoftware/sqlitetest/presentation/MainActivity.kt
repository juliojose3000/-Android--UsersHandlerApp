package com.loaizasoftware.sqlitetest.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loaizasoftware.sqlitetest.data.database.MyDatabaseHelper
import com.loaizasoftware.sqlitetest.domain.User
import com.loaizasoftware.sqlitetest.presentation.theme.SQLiteTestTheme
import com.loaizasoftware.sqlitetest.presentation.ui.screens.ShowAllScreen

class MainActivity : ComponentActivity() {

    private lateinit var dbHelper: MyDatabaseHelper

    private lateinit var users: List<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDB(context = this)

        enableEdgeToEdge()
        setContent {
            SQLiteTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SQLiteTest(users, paddingValues = innerPadding)
                }
            }
        }


        3
    }

    private infix fun initDB(context: Context) {

        dbHelper = MyDatabaseHelper(context)

        dbHelper.insertUser("Julio", 26)

        users = dbHelper.getAllUsers()

        for (user in users) {
            Log.v("MyTAG", "Name: ${user.name}, Age: ${user.age}")
        }

    }

}

@Composable
fun SQLiteTest(users: List<User>, paddingValues: PaddingValues) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "show_all_users") {
        composable("show_all_users") {
            ShowAllScreen(users = users)
        }
    }

}

@Composable
fun HomeScreen(paddingValues: PaddingValues) {

    Column(modifier = Modifier.padding(paddingValues)) {

    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SQLiteTestTheme {
        //HomeScreen()
    }
}