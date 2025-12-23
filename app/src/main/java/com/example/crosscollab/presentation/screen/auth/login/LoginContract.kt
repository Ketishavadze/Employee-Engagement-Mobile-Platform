package com.example.crosscollab.presentation.screen.auth.login

/**
 * Login Contract defining UI State, Events, and Effects
 * Following MVI/Clean Architecture pattern
 */
object LoginContract {

    /**
     * UI State for the Login Screen
     */
    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val emailError: String? = null,
        val passwordError: String? = null,
        val rememberMe: Boolean = false,
        val isSignInEnabled: Boolean = false
    )

    /**
     * User Events/Actions from the UI
     */
    sealed class Event {
        data class OnEmailChanged(val email: String) : Event()
        data class OnPasswordChanged(val password: String) : Event()
        data class OnRememberMeChanged(val checked: Boolean) : Event()
        object OnSignInClicked : Event()
        object OnForgotPasswordClicked : Event()
        object OnSignUpClicked : Event()
    }

    /**
     * One-time UI Effects (Navigation, Messages, etc.)
     */
    sealed class Effect {
        object NavigateToHome : Effect()
        object NavigateToForgotPassword : Effect()
        object NavigateToSignUp : Effect()
        data class ShowError(val message: String) : Effect()
        data class ShowToast(val message: String) : Effect()
    }
}