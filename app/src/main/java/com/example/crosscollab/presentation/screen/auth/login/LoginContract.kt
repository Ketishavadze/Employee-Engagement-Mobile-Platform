package com.example.crosscollab.presentation.screen.auth.login
// State - What the UI displays
data class LoginState(
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)

// Event - User actions
sealed class LoginEvent {
    data class OnEmailChanged(val email: String)
    data class OnPasswordChanged(val password: String)
    data class OnLoginClicked(val email: String, val password: String)
    object OnForgotPasswordClicked
    object OnSignUpClicked
}

// SideEffect - One-time events
sealed class LoginSideEffect {
    object NavigateToHome
    object NavigateToRegister
    object NavigateToForgotPassword
    data class ShowError(val message: String)
    data class ShowSuccess(val message: String)
}