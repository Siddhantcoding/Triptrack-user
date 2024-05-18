package com.project.trip_track_project.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.trip_track_project.R
import com.project.trip_track_project.ui.theme.Trip_Track_ProjectTheme


@Composable
fun LoginScreen(
    state: LoginState = LoginState(),
    onEvent: (LoginEvent) -> Unit,
    onNavigateToRegister: () -> Unit,
    gotoHome: () -> Unit
) {

    if (state.isLoginSuccess) {
        gotoHome()
    }
    Box {
        Image(
            painter = painterResource(id = R.drawable.login_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            AnimatedVisibility(visible = state.error.isNotEmpty()) {
                Text(
                    text = state.error,
                    modifier = Modifier.padding(16.dp),
                    color = androidx.compose.ui.graphics.Color.Red
                )
            }

            AnimatedVisibility(visible = state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp)
                )
            }
            AnimatedVisibility(visible = !state.isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = state.email,
                        onValueChange = { onEvent(LoginEvent.SetEmail(it)) },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = state.password,
                        onValueChange = { onEvent(LoginEvent.SetPassword(it)) },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { onEvent(LoginEvent.OnLogin) }) {
                        Text("Login")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Don't have an account?",
                        modifier = Modifier.clickable {
                            onNavigateToRegister()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    Trip_Track_ProjectTheme {
        LoginScreen(
            state = LoginState(),
            onEvent = {},
            onNavigateToRegister = {},
            gotoHome = {}
        )
    }
}

