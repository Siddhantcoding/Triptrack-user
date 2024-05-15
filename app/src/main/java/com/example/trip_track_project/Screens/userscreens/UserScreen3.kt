package com.example.trip_track_project.Screens.userscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen3() {
    var text by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ), title = {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center, //make the text bold
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    fontSize = 30.sp,
                    text = "TripTrack",
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    Modifier.size(35.dp)
                )
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    Modifier
                        .offset(x = 325.dp)
                        .size(35.dp)
                )
            })
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {


        }
    }
}


@Preview
@Composable
private fun UserScreen3Preview () {
    UserScreen3()

}