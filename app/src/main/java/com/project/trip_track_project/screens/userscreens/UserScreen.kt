package com.project.trip_track_project.screens.userscreens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.trip_track_project.R
import com.project.trip_track_project.ui.theme.Trip_Track_ProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    state: UserScreenState = UserScreenState(),
    onEvent: (UserScreenEvent) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "menu",
                            Modifier.size(35.dp)
                        )
                    }
                },
                title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center, //make the text bold
                    style = TextStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                    fontSize = 24.sp,
                    text = "TripTrack",
                )
                }, actions = {
                    IconButton(onClick = {
                        onEvent(UserScreenEvent.onLogoutSelected)
                    }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "")
                    }
            })
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mobile_abstract_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(180.dp)
                            .weight(1f)
                            .clickable {
                                onEvent(UserScreenEvent.onProfileSelected)
                            }
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = .2f),
                                        Color.LightGray
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Text(text = "Your Profile", textAlign = TextAlign.Center)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(180.dp)
                            .weight(1f)
                            .clickable {
                                 onEvent(UserScreenEvent.onLocationSelected)                            }
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = .2f),
                                        Color.LightGray
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Text(text = "My Location Setting", textAlign = TextAlign.Center)
                    }
                }
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(180.dp)
                            .weight(1f)
                            .clickable {
                                onEvent(UserScreenEvent.onTaxiSelected)
                            }
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = .2f),
                                        Color.LightGray
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.car_easy),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Text(text = "Find Local Taxi", textAlign = TextAlign.Center)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(180.dp)
                            .weight(1f)
                            .clickable {
                                onEvent(UserScreenEvent.onRickshawSelected)
                            }
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = .2f),
                                        Color.LightGray
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.rickshaw),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Text(text = "Find E-Rikshaw", textAlign = TextAlign.Center)
                    }
                }

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(180.dp)
                            .weight(1f)
                            .clickable {
                                onEvent(UserScreenEvent.onBusSelected)
                            }
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = .2f),
                                        Color.LightGray
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.bus),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Text(text = "Find Local Bus", textAlign = TextAlign.Center)
                    }
                }

            }
        }
    }
}
@Preview
@Composable
private fun UserScreenPreview() {
    Trip_Track_ProjectTheme {
        UserScreen()
    }

}