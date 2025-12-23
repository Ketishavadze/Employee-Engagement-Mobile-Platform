package com.example.crosscollab.presentation.screen.auth.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginContract.State())
    val state: StateFlow<LoginContract.State> = _state.asStateFlow()

    private val _effect = Channel<LoginContract.Effect>()
    val effect = _effect.receiveAsFlow()

    /**
     * Handle UI events
     */
    fun onEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnEmailChanged -> {
                updateEmail(event.email)
            }
            is LoginContract.Event.OnPasswordChanged -> {
                updatePassword(event.password)
            }
            is LoginContract.Event.OnRememberMeChanged -> {
                updateRememberMe(event.checked)
            }
            LoginContract.Event.OnSignInClicked -> {
                signIn()
            }
            LoginContract.Event.OnForgotPasswordClicked -> {
                navigateToForgotPassword()
            }
            LoginContract.Event.OnSignUpClicked -> {
                navigateToSignUp()
            }
        }
    }

    private fun updateEmail(email: String) {
        _state.update { currentState ->
            currentState.copy(
                email = email,
                emailError = null,
                isSignInEnabled = validateForm(email, currentState.password)
            )
        }
    }

    private fun updatePassword(password: String) {
        _state.update { currentState ->
            currentState.copy(
                password = password,
                passwordError = null,
                isSignInEnabled = validateForm(currentState.email, password)
            )
        }
    }

    private fun updateRememberMe(checked: Boolean) {
        _state.update { it.copy(rememberMe = checked) }
    }

    private fun signIn() {
        val currentState = _state.value

        // Validate inputs
        val emailError = validateEmail(currentState.email)
        val passwordError = validatePassword(currentState.password)

        if (emailError != null || passwordError != null) {
            _state.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            return
        }

        // Show loading
        _state.update { it.copy(isLoading = true) }

        // Simulate API call
        viewModelScope.launch {
            try {
                // TODO: Replace with actual authentication API call
                // val result = authRepository.login(currentState.email, currentState.password)

                // Simulate network delay
                delay(2000)

                // For demo purposes, accept any valid email/password
                // In real app, check API response
                if (currentState.email.isNotEmpty() && currentState.password.isNotEmpty()) {
                    // Save remember me preference
                    if (currentState.rememberMe) {
                        // TODO: Save credentials to preferences
                        // preferencesManager.saveRememberMe(true)
                    }

                    _state.update { it.copy(isLoading = false) }
                    _effect.send(LoginContract.Effect.NavigateToHome)
                } else {
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(LoginContract.Effect.ShowError("Invalid credentials"))
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                _effect.send(
                    LoginContract.Effect.ShowError(
                        e.message ?: "An error occurred during sign in"
                    )
                )
            }
        }
    }

    private fun navigateToForgotPassword() {
        viewModelScope.launch {
            _effect.send(LoginContract.Effect.NavigateToForgotPassword)
        }
    }

    private fun navigateToSignUp() {
        viewModelScope.launch {
            _effect.send(LoginContract.Effect.NavigateToSignUp)
        }
    }

    /**
     * Validation methods
     */
    private fun validateForm(email: String, password: String): Boolean {
        return email.isNotBlank() && password.isNotBlank() &&
                validateEmail(email) == null && validatePassword(password) == null
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Please enter a valid email address"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }
}