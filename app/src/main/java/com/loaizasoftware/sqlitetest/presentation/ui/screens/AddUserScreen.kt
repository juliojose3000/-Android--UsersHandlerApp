package com.loaizasoftware.sqlitetest.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.loaizasoftware.sqlitetest.presentation.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserScreen(
    navHostController: NavHostController,
    //addUserEvent: (name: String, age: Int) -> Boolean,
    viewModel: UserViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add User") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                navHostController.navigateUp()
                            }
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->

        UserForm(
            padding = padding,
            viewModel = viewModel
        )

    }

}

@Composable
fun UserForm(
    padding: PaddingValues,
    viewModel: UserViewModel?,
) {

    requireNotNull(viewModel)

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        HeadersAndInputs(
            modifier = Modifier.weight(1f),
            viewModel = viewModel
        )

        Button(
            onClick = { viewModel.addUser() },
            modifier = Modifier.
                padding(16.dp)
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Add User", style = TextStyle(fontSize = 20.sp))
        }

    }

}

@Composable
fun HeadersAndInputs(modifier: Modifier, viewModel: UserViewModel) {

    val name by viewModel.nameState.collectAsState()
    val age: Int by viewModel.ageState.collectAsState()

    Column(
        modifier = modifier
    ) {

        Text(
            text = "Name",
            style = TextStyle(
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(8.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.setName(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Text(
            text = "Age",
            style = TextStyle(
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(8.dp)
        )

        OutlinedTextField(
            value = age.toString(),
            onValueChange = { viewModel.setAge(it.toIntOrNull() ?: 0) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

    }

}



@Preview
@Composable
fun AddUserScreenPreview() {
    UserForm(padding = PaddingValues(16.dp), viewModel = null)
}