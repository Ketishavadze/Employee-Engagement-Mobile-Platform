package com.example.crosscollab.presentation.screen.auth.forgotpassword

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ForgotPasswordViewModel : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordContract.State())
    val state: StateFlow<ForgotPasswordContract.State> = _state.asStateFlow()

    private val _effect = Channel<ForgotPasswordContract.Effect>()
    val effect = _effect.receiveAsFlow()

    /**
     * Handle UI events
     */
    fun onEvent(event: ForgotPasswordContract.Event) {
        when (event) {
            is ForgotPasswordContract.Event.OnEmailChanged -> {
                updateEmail(event.email)
            }
            ForgotPasswordContract.Event.OnSendResetLinkClicked -> {
                sendResetLink()
            }
            ForgotPasswordContract.Event.OnBackToSignInClicked -> {
                navigateToLogin()
            }
        }
    }

    private fun updateEmail(email: String) {
        _state.update { currentState ->
            currentState.copy(
                email = email,
                emailError = null,
                isSendButtonEnabled = validateEmail(email) == null
            )
        }
    }

    private fun sendResetLink() {
        val currentState = _state.value

        // Validate email
        val emailError = validateEmail(currentState.email)
        if (emailError != null) {
            _state.update { it.copy(emailError = emailError) }
            return
        }

        // Show loading
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                // TODO: Replace with actual password reset API call
                // val result = authRepository.sendPasswordResetLink(currentState.email)

                // Simulate network delay
                delay(2000)

                _state.update { it.copy(isLoading = false) }
                _effect.send(ForgotPasswordContract.Effect.ShowResetLinkSentSuccess)

                // Optionally navigate back to login after showing success
                delay(1500)
                _effect.send(ForgotPasswordContract.Effect.NavigateToLogin)
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                _effect.send(
                    ForgotPasswordContract.Effect.ShowError(
                        e.message ?: "Failed to send reset link. Please try again."
                    )
                )
            }
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            _effect.send(ForgotPasswordContract.Effect.NavigateToLogin)
        }
    }

    /**
     * Validation methods
     */
    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Please enter a valid email address"
            else -> null
        }
    }
}