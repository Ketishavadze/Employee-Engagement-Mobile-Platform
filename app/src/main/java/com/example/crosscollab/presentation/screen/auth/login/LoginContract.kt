package com.example.crosscollab.presentation.screen.auth.login


object LoginContract {

    data class State(
        val email: String = "",
        val password: String = "",
        val rememberMe: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface Event {
        data class EmailChanged(val value: String) : Event
        data class PasswordChanged(val value: String) : Event
        data class RememberMeChanged(val checked: Boolean) : Event

        object SignInClicked : Event
        object ForgotPasswordClicked : Event
        object SignUpClicked : Event
    }

    sealed interface Effect {
        object NavigateToHome : Effect
        object NavigateToRegister : Effect
        object NavigateToForgotPassword : Effect
        data class ShowToast(val message: String) : Effect
    }
}
