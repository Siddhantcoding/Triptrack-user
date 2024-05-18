package com.project.trip_track_project.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.trip_track_project.R
import com.project.trip_track_project.ui.theme.Trip_Track_ProjectTheme

@Composable
fun CompleteSignUp(
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center

    ) {
        Image(painter = painterResource(id = R.drawable.login_bg),
            contentDescription = "Background Image" ,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        IconButton(onClick = { onBack() },
            modifier = Modifier.align(Alignment.TopStart)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                Modifier.size(50.dp),
                tint = Color.White
            )

        }

        Column {
            Text(
                text = "Complete Sign Up",
                color = Color.White,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("First Name") }
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Last Name") }
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Phone Number") }
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Email") }
            )
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.offset(x = 100.dp, y = 20.dp)) {
                Text("Submit")
                
            }


        }
    }
    
}


@Preview(showBackground = true)
@Composable
private fun CompleteSignUpPreview() {
    Trip_Track_ProjectTheme {
        CompleteSignUp()
    }
    
}