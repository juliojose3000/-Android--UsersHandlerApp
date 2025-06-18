package com.loaizasoftware.usershandlerapp.presentation.ui.activities

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
import com.loaizasoftware.usershandlerapp.data.database.MyDatabaseHelper
import com.loaizasoftware.usershandlerapp.data.repositories_impl.UserRepositoryImpl
import com.loaizasoftware.usershandlerapp.domain.usecase.AddUserUseCase
import com.loaizasoftware.usershandlerapp.domain.usecase.DeleteUserByIdUseCase
import com.loaizasoftware.usershandlerapp.domain.usecase.GetAllUsersUseCase
import com.loaizasoftware.usershandlerapp.presentation.theme.usershandlerappTheme
import com.loaizasoftware.usershandlerapp.presentation.ui.screens.AddUserScreen
import com.loaizasoftware.usershandlerapp.presentation.ui.screens.AllUsersScreen
import com.loaizasoftware.usershandlerapp.presentation.viewmodels.UserViewModel

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
            usershandlerappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    usershandlerapp(
                        paddingValues = innerPadding,
                        viewModel = viewModel
                    )
                }
            }
        }

    }

}

@Composable
fun usershandlerapp(
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
    usershandlerappTheme {
        //HomeScreen()
    }
}