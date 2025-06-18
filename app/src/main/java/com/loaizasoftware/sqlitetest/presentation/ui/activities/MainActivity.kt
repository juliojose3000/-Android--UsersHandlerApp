package com.loaizasoftware.sqlitetest.presentation.ui.activities

import android.os.Bundle
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
import com.loaizasoftware.sqlitetest.domain.usecase.AddUserUseCase
import com.loaizasoftware.sqlitetest.domain.usecase.DeleteUserByIdUseCase
import com.loaizasoftware.sqlitetest.domain.usecase.GetAllUsersUseCase
import com.loaizasoftware.sqlitetest.presentation.theme.SQLiteTestTheme
import com.loaizasoftware.sqlitetest.presentation.ui.screens.AddUserScreen
import com.loaizasoftware.sqlitetest.presentation.ui.screens.AllUsersScreen
import com.loaizasoftware.sqlitetest.presentation.viewmodels.UserViewModel

class MainActivity : ComponentActivity() {

    private lateinit var dbHelper: MyDatabaseHelper

    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = MyDatabaseHelper(this)

        val useCase = AddUserUseCase(repository = UserRepositoryImpl(dbHelper))
        val getAllUsersUseCase = GetAllUsersUseCase(repository = UserRepositoryImpl(dbHelper))
        val deleteUserByIdUseCase = DeleteUserByIdUseCase(repository = UserRepositoryImpl(dbHelper))

        viewModel = UserViewModel(
            addUserUseCase = useCase,
            getAllUsersUseCase = getAllUsersUseCase,
            deleteUserByIdUseCase = deleteUserByIdUseCase
        )

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

            AllUsersScreen(
                users = users,
                navHostController = navController,
                deleteUserEvent = viewModel::deleteUserById
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