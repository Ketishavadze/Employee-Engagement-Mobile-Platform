package com.example.crosscollab.presentation.screen.auth.login


object LoginContract {

    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val emailError: String? = null,
        val passwordError: String? = null,
        val rememberMe: Boolean = false,
        val isSignInEnabled: Boolean = false
    )

    sealed class Event {
        data class OnEmailChanged(val email: String) : Event()
        data class OnPasswordChanged(val password: String) : Event()
        data class OnRememberMeChanged(val checked: Boolean) : Event()
        object OnSignInClicked : Event()
        object OnForgotPasswordClicked : Event()
        object OnSignUpClicked : Event()
    }

    sealed class Effect {
        object NavigateToHome : Effect()
        object NavigateToForgotPassword : Effect()
        object NavigateToSignUp : Effect()
        data class ShowError(val message: String) : Effect()
        data class ShowToast(val message: String) : Effect()
    }
}