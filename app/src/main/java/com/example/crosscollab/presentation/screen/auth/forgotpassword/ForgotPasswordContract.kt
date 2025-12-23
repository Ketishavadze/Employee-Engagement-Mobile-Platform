package com.example.crosscollab.presentation.screen.auth.forgotpassword


object ForgotPasswordContract {


    data class State(
        val email: String = "",
        val isLoading: Boolean = false,
        val emailError: String? = null,
        val isSendButtonEnabled: Boolean = false
    )


    sealed class Event {
        data class OnEmailChanged(val email: String) : Event()
        object OnSendResetLinkClicked : Event()
        object OnBackToSignInClicked : Event()
    }


    sealed class Effect {
        object NavigateToLogin : Effect()
        object ShowResetLinkSentSuccess : Effect()
        data class ShowError(val message: String) : Effect()
        data class ShowToast(val message: String) : Effect()
    }
}