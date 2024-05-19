package com.project.trip_track_project.screens.profile

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.CircularProgressIndicator
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
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit = {},
    onBack: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center

    ) {
        Image(
            painter = painterResource(id = R.drawable.mobile_abstract_bg),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = { onBack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                Modifier.size(50.dp),
                tint = Color.White
            )

        }
        AnimatedVisibility(visible = state.status == ProfileStatus.LOADING) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
        AnimatedVisibility(visible = state.status == ProfileStatus.IDLE ) {
            Column {
                Text(
                    text = "Complete Sign Up",
                    color = Color.White,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.size(20.dp))
                TextField(
                    value = state.firstname,
                    onValueChange = { onEvent(ProfileEvent.OnFirstNameChange(it)) },
                    label = { Text("First Name") }
                )
                Spacer(modifier = Modifier.size(20.dp))
                TextField(
                    value = state.lastname,
                    onValueChange = { onEvent(ProfileEvent.OnLastNameChange(it)) },
                    label = { Text("Last Name") }
                )
                Spacer(modifier = Modifier.size(20.dp))
                TextField(
                    value = state.phone,
                    onValueChange = { onEvent(ProfileEvent.OnPhoneChange(it)) },
                    label = { Text("Phone Number") }
                )
                Spacer(modifier = Modifier.size(20.dp))
                TextField(
                    value = state.email,
                    onValueChange = { onEvent(ProfileEvent.OnEmailChange(it)) },
                    label = { Text("Email") }
                )
                Button(
                    onClick = {
                        onEvent(ProfileEvent.SaveProfile)
                    },
                    modifier = Modifier.offset(x = 100.dp, y = 20.dp)
                ) {
                    Text("Submit")

                }
            }
        }

        AnimatedVisibility(visible = state.status == ProfileStatus.SUCCESS) {
            Text(
                text = "Profile Updated Successfully",
                color = Color.White,
                fontSize = 30.sp
            )
        }
        AnimatedVisibility(visible = state.status == ProfileStatus.ERROR) {
            Text(
                text = "Profile Update Failed",
                color = Color.White,
                fontSize = 30.sp
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun CompleteSignUpPreview() {
    Trip_Track_ProjectTheme {
        ProfileScreen(
            state = ProfileState(),
            onEvent = {},
            onBack = { }
        )
    }

}