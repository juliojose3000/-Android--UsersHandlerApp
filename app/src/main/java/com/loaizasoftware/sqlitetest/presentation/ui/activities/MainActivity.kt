package com.loaizasoftware.sqlitetest.presentation.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loaizasoftware.sqlitetest.data.database.MyDatabaseHelper
import com.loaizasoftware.sqlitetest.data.repositories_impl.UserRepositoryImpl
import com.loaizasoftware.sqlitetest.domain.model.User
import com.loaizasoftware.sqlitetest.presentation.theme.SQLiteTestTheme
import com.loaizasoftware.sqlitetest.presentation.ui.screens.AddUserScreen
import com.loaizasoftware.sqlitetest.presentation.ui.screens.ShowAllScreen
import com.loaizasoftware.sqlitetest.presentation.viewmodels.UserViewModel

class MainActivity : ComponentActivity() {

    private lateinit var dbHelper: MyDatabaseHelper

    private lateinit var users: List<User>

    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDB(context = this)

        viewModel = UserViewModel(repository = UserRepositoryImpl(dbHelper))

        enableEdgeToEdge()
        setContent {
            SQLiteTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SQLiteTest(
                        paddingValues = innerPadding,
                        viewModel = viewModel
                    )
                }
            }
        }

    }

    private infix fun initDB(context: Context) {

        dbHelper = MyDatabaseHelper(context)

        /*dbHelper.deleteAllUsers()

        dbHelper.insertUser("Julio", 26)
        dbHelper.insertUser("Andres", 33)

        users = dbHelper.getAllUsers()

        for (user in users) {
            Log.v("MyTAG", "Name: ${user.name}, Age: ${user.age}")
        }*/

    }

}

@Composable
fun SQLiteTest(
    paddingValues: PaddingValues,
    viewModel: UserViewModel
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "show_all_users") {

        composable("show_all_users") {

            //val users = viewModel.listUsers.value
            val users = viewModel.listUsers.collectAsState().value

            ShowAllScreen(
                users = users,
                navHostController = navController
            )
        }

        composable("add_user") {
            AddUserScreen(
                navHostController = navController,
                viewModel = viewModel
            )
        }

    }

}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SQLiteTestTheme {
        //HomeScreen()
    }
}