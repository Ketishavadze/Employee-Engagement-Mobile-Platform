package com.example.crosscollab.presentation.screen.auth.forgotpassword

data class ForgotPasswordState(
    val isLoading: Boolean = false,
    val emailError: String? = null
)

sealed class ForgotPasswordEvent {
    data class OnEmailChanged(val email: String)
    data class OnSendResetLinkClicked(val email: String)
    object OnBackToSignInClicked
}

sealed class ForgotPasswordSideEffect {
    object NavigateToLogin
    data class ShowError(val message: String)
    data class ShowSuccess(val message: String)
}