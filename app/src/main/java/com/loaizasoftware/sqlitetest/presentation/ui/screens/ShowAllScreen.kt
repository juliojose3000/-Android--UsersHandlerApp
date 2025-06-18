package com.loaizasoftware.sqlitetest.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
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

        LazyColumn(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
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
                        text = users[index].name,
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