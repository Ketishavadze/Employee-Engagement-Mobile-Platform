package com.example.crosscollab.presentation.screen.auth.forgotpassword

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crosscollab.domain.usecase.auth.ForgotPasswordUseCase
import com.example.crosscollab.presentation.common.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ForgotPasswordViewModel(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : BaseViewModel<
        ForgotPasswordContract.State,
        ForgotPasswordContract.Event,
        ForgotPasswordContract.Effect
        >(ForgotPasswordContract.State()) {

    override fun onEvent(event: ForgotPasswordContract.Event) {
        when (event) {

            is ForgotPasswordContract.Event.EmailChanged ->
                updateState { it.copy(email = event.value) }

            ForgotPasswordContract.Event.SendResetLinkClicked ->
                sendResetLink()

            ForgotPasswordContract.Event.BackToSignInClicked ->
                emitSideEffect(ForgotPasswordContract.Effect.NavigateBackToSignIn)
        }
    }

    private fun sendResetLink() = viewModelScope.launch {
        val email = state.value.email

        if (email.isBlank()) {
            emitSideEffect(
                ForgotPasswordContract.Effect.ShowToast(
                    "Please enter your email"
                )
            )
            return@launch
        }

        updateState { it.copy(isLoading = true) }

        runCatching {
            forgotPasswordUseCase(email)
        }.onSuccess {
            emitSideEffect(
                ForgotPasswordContract.Effect.ShowToast(
                    "Reset link sent to your email"
                )
            )
        }.onFailure {
            emitSideEffect(
                ForgotPasswordContract.Effect.ShowToast(
                    it.message ?: "Something went wrong"
                )
            )
        }

        updateState { it.copy(isLoading = false) }
    }
}
