package com.example.crosscollab.presentation.screen.auth.login

import androidx.lifecycle.viewModelScope
import com.example.crosscollab.domain.usecase.auth.ForgotPasswordUseCase
import com.example.crosscollab.domain.usecase.auth.LoginUseCase
import com.example.crosscollab.presentation.common.BaseViewModel
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : BaseViewModel<
        LoginContract.State,
        LoginContract.Event,
        LoginContract.Effect
        >(LoginContract.State()) {

    override fun onEvent(event: LoginContract.Event) {
        when (event) {

            is LoginContract.Event.EmailChanged ->
                updateState { it.copy(email = event.value) }

            is LoginContract.Event.PasswordChanged ->
                updateState { it.copy(password = event.value) }

            is LoginContract.Event.RememberMeChanged ->
                updateState { it.copy(rememberMe = event.checked) }

            LoginContract.Event.SignInClicked -> login()
            LoginContract.Event.SignUpClicked ->
                emitSideEffect(LoginContract.Effect.NavigateToRegister)

            LoginContract.Event.ForgotPasswordClicked ->
                emitSideEffect(LoginContract.Effect.NavigateToForgotPassword)
        }
    }

    private fun login() = viewModelScope.launch {
        val s = state.value

        updateState { it.copy(isLoading = true) }

        runCatching {
            loginUseCase(s.email, s.password)
        }.onSuccess {
            emitSideEffect(LoginContract.Effect.NavigateToHome)
        }.onFailure {
            emitSideEffect(
                LoginContract.Effect.ShowToast(
                    it.message ?: "Login failed"
                )
            )
        }

        updateState { it.copy(isLoading = false) }
    }
}
