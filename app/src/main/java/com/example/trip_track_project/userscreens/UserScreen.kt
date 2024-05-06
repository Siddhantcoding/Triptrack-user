package com.example.trip_track_project.userscreens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.trip_track_project.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen() {
    var text by remember { mutableIntStateOf(0)}

    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center, //make the text bold
                    style = TextStyle(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    ),
                    fontSize = 30.sp,
                    text = "TripTrack",
                )
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "menu",
                    Modifier.size(35.dp)
                )
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Account",
                    Modifier
                        .offset(x = 325.dp)
                        .size(35.dp)
                )
            })
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(35.dp)
                        .offset(x = 325.dp)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "info",
                    modifier = Modifier
                        .size(35.dp)
                        .offset(x = 210.dp)
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "search",
                    modifier = Modifier
                        .size(35.dp)
                        .offset(x = 100.dp)
                )
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "add",
                    modifier = Modifier
                        .size(35.dp)
                        .offset(x = 0.dp)
                )
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "home",
                    modifier = Modifier
                        .size(35.dp)
                        .offset(x = (-100).dp)
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.size(12.dp))
            OutlinedTextField(
                value = TextFieldValue(), onValueChange = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                placeholder = { Text("Search Destination") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
            )
            ElevatedCard(
                modifier = Modifier
                    .size(170.dp)
                    .padding(10.dp),
            ) {
                Image(painter = painterResource(id = R.drawable.auto4), contentDescription = "Auto",
                    modifier = Modifier
                        .fillMaxSize())





            }
            ElevatedCard(
                modifier = Modifier
                    .size(170.dp)
                    .padding(10.dp)
                    .offset(x = 220.dp, y = (-185).dp),
            ) {
                Image(painter = painterResource(id = R.drawable.car1), contentDescription = "Car",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White))


            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                ElevatedCard(
                    modifier = Modifier
                        .width(375.dp)
                        .height(170.dp)
                        .offset(x = 10.dp, y = (-170).dp)
                        .padding(16.dp),
                ) {


                }
            }

        }
    }
}


@Preview
@Composable
private fun UserScreenPreview() {
    UserScreen()

}