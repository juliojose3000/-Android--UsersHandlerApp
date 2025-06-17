package com.loaizasoftware.sqlitetest.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loaizasoftware.sqlitetest.data.dummy.users
import com.loaizasoftware.sqlitetest.domain.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAllScreen(users: List<User>) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "List users") })
        }
    ) { padding ->

        LazyColumn(modifier = Modifier.padding(padding)) {

            items(users.size) { index ->

                Row(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = users[index].name,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = users[index].age.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

            }

        } //Column

    }

}


@Preview
@Composable
fun ShowAllScreenPreview() {
    ShowAllScreen(users = users)
}