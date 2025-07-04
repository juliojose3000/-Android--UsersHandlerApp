package com.loaizasoftware.usershandlerapp.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.loaizasoftware.usershandlerapp.R
import com.loaizasoftware.usershandlerapp.data.dummy.users
import com.loaizasoftware.usershandlerapp.domain.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllUsersScreen(
    users: List<User>,
    navHostController: NavHostController,
    deleteUserEvent: (userId: Int) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "List users") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                //navHostController.navigateUp()
                            }
                    )
                }
            )
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            UsersTable(
                users = users,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    ),
                deleteUserEvent = deleteUserEvent

            )

            Button(
                modifier = Modifier
                    .height(56.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    navHostController.navigate("add_user")
                }
            ) {
                Text(text = "Add User")
            }

        }

    }

}

@Composable
fun UsersTable(users: List<User>, modifier: Modifier, deleteUserEvent: (userId: Int) -> Unit) {

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
    ) {

        items(users.size) { index ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(border = BorderStroke(width = 1.dp, color = Color.LightGray))
                    .height(IntrinsicSize.Min) // 🔑 This makes Row wrap its tallest child
            ) {

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    text = users[index].name ?: "",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .border(border = BorderStroke(width = 1.dp, color = Color.LightGray))
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    text = users[index].age.toString(),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .border(border = BorderStroke(width = 1.dp, color = Color.LightGray))
                )

                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(35.dp)
                        .clickable {
                            deleteUserEvent(users[index].id ?: -1)
                        },
                    imageVector = Icons.Default.Delete,
                    contentDescription = ""
                )

                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .border(border = BorderStroke(width = 1.dp, color = Color.LightGray))
                )

                val syncIconRes =
                    if(users[index].synced)
                        R.drawable.cloud_24px
                else
                    R.drawable.backup_24px

                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(35.dp),
                    painter = painterResource(id = syncIconRes),
                    contentDescription = ""
                )

            }

        }

    } //Column

}


@Preview
@Composable
fun ShowAllScreenPreview() {

    UsersTable(
        users = users,
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .background(Color.White),
        deleteUserEvent = { true }

    )

}