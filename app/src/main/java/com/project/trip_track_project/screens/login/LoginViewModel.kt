package com.project.trip_track_project.screens.login

import androidx.lifecycle.ViewModel
import com.project.trip_track_project.screens.auth.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class LoginViewModel(
    private val authService: AuthService = AuthService()
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnLogin -> {
                try {

                    authService.login(_state)
                } catch (e: Exception) {
                    _state.update { state ->
                        state.copy(error = e.message ?: "An error occurred")
                    }
                }
            }

            is LoginEvent.SetEmail -> {
                _state.update { state ->
                    state.copy(email = event.email)
                }
            }

            is LoginEvent.SetPassword -> {
                _state.update { state ->
                    state.copy(password = event.password)
                }
            }

            LoginEvent.ClearError -> {
                _state.update { state ->
                    state.copy(error = "")
                }
            }


        }
    }
}