package com.example.crosscollab.presentation.screen.auth.forgotpassword

/**
 * Forgot Password Contract defining UI State, Events, and Effects
 */
object ForgotPasswordContract {

    /**
     * UI State for the Forgot Password Screen
     */
    data class State(
        val email: String = "",
        val isLoading: Boolean = false,
        val emailError: String? = null,
        val isSendButtonEnabled: Boolean = false
    )

    /**
     * User Events/Actions from the UI
     */
    sealed class Event {
        data class OnEmailChanged(val email: String) : Event()
        object OnSendResetLinkClicked : Event()
        object OnBackToSignInClicked : Event()
    }

    /**
     * One-time UI Effects
     */
    sealed class Effect {
        object NavigateToLogin : Effect()
        object ShowResetLinkSentSuccess : Effect()
        data class ShowError(val message: String) : Effect()
        data class ShowToast(val message: String) : Effect()
    }
}