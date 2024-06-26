import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.trip_track_project.screens.register.RegisterEvent
import com.project.trip_track_project.screens.register.RegisterState
import com.project.trip_track_project.ui.theme.Trip_Track_ProjectTheme

@Composable
fun RegisterScreen(
    state: RegisterState = RegisterState(),
    onEvent: (RegisterEvent) -> Unit = {},
    onBack: () -> Unit = {},
    gotoHome: () -> Unit = {}
) {
    if (state.isRegisterSuccess) {
        gotoHome()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer)

        ){
            IconButton(
                onClick = { onBack() },
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.onTertiary,
                    MaterialTheme.shapes.small
                )
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.width(280.dp)
        ) {

            Text(text = "Create free account", style = MaterialTheme.typography.headlineMedium)
            AnimatedVisibility(visible = state.error.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = state.error,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { onEvent(RegisterEvent.ClearError) },
                            modifier = Modifier.size(18.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "close",
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
            if(state.isLoading){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }else{
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { onEvent(RegisterEvent.SetEmail(it)) },
                    placeholder = { Text("Email") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
                )
                //
                OutlinedTextField(
                    value = state.username,
                    onValueChange = { onEvent(RegisterEvent.SetUsername(it)) },
                    placeholder = { Text("Username") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    }
                )
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { onEvent(RegisterEvent.SetPassword(it)) },
                    placeholder = { Text("Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },

                    )
                OutlinedTextField(
                    value = state.confirmPassword,
                    onValueChange = { onEvent(RegisterEvent.SetConfirmPassword(it)) },
                    placeholder = { Text("Confirm Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    }
                )
            }
            Row {
                Button(
                    onClick = { onEvent(RegisterEvent.OnSaveUser) },
                    shape = MaterialTheme.shapes.extraSmall,
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        Text(text = "Registering ...")
                    } else {
                        Text("Register")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RegisterPreview() {
    Trip_Track_ProjectTheme {

        RegisterScreen(
            state = RegisterState(
                email = "xaidmeta@gmail.com",
                username = "xaidmeta",
                password = "123456",
                confirmPassword = "",
                error = "error message",
                isLoading = true,
                isRegisterSuccess = false
            )
        )
    }
}
