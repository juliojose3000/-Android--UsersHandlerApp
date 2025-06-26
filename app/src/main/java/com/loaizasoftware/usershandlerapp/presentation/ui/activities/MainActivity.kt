package com.loaizasoftware.usershandlerapp.presentation.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.google.android.gms.common.GoogleApiAvailability
import com.loaizasoftware.usershandlerapp.presentation.theme.usershandlerappTheme
import com.loaizasoftware.usershandlerapp.presentation.ui.screens.AddUserScreen
import com.loaizasoftware.usershandlerapp.presentation.ui.screens.AllUsersScreen
import com.loaizasoftware.usershandlerapp.presentation.viewmodels.UserViewModel
import com.loaizasoftware.usershandlerapp.utils.workers.SyncUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint //Indicates Hilt that this class will require dependencies
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            usershandlerappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        paddingValues = innerPadding,
                        viewModel = viewModel
                    )
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        SyncUtils.setupFirestoreRealTimeUpdates(this)
    }

}

@Composable
fun Navigation(
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