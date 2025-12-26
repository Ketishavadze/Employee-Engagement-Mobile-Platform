package com.example.crosscollab.presentation.screen.auth.forgotpassword


object ForgotPasswordContract {

    data class State(
        val email: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface Event {
        data class EmailChanged(val value: String) : Event
        object SendResetLinkClicked : Event
        object BackToSignInClicked : Event
    }

    sealed interface Effect {
        object NavigateBackToSignIn : Effect
        data class ShowToast(val message: String) : Effect
    }
}
