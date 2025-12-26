package com.example.crosscollab.presentation.screen.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crosscollab.domain.usecase.auth.GetDepartmentsUseCase
import com.example.crosscollab.domain.usecase.auth.RegisterUseCase
import com.example.crosscollab.domain.usecase.auth.SendOtpUseCase
import com.example.crosscollab.domain.usecase.auth.VerifyOtpUseCase
import com.example.crosscollab.presentation.common.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val getDepartments: GetDepartmentsUseCase,
    private val sendOtp: SendOtpUseCase,
    private val verifyOtp: VerifyOtpUseCase,
    private val register: RegisterUseCase
) : BaseViewModel<
        RegisterContract.State,
        RegisterContract.Event,
        RegisterContract.Effect
        >(RegisterContract.State()) {

    override fun onEvent(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.FirstNameChanged ->
                updateState { it.copy(firstName = event.value) }

            is RegisterContract.Event.PolicyChecked ->
                updateState { it.copy(agreePolicy = event.checked) }

            RegisterContract.Event.LoadDepartments -> loadDepartments()
            RegisterContract.Event.SendOtp -> sendOtp()
            RegisterContract.Event.CreateAccount -> registerAccount()
            RegisterContract.Event.SignInClicked ->
                emitSideEffect(RegisterContract.Effect.NavigateToSignIn)

            else -> Unit
        }
    }

    private fun loadDepartments() = viewModelScope.launch {
        updateState { it.copy(isLoading = true) }
        val list = getDepartments()
        updateState { it.copy(isLoading = false, departments = list) }
    }

    private fun sendOtp() = viewModelScope.launch {
        updateState { it.copy(isSendingOtp = true) }
        val seconds = sendOtp(state.value.phoneNumber)
        updateState {
            it.copy(
                isSendingOtp = false,
                otpExpirySeconds = seconds
            )
        }
    }

    private fun registerAccount() = viewModelScope.launch {
        val s = state.value

        updateState { it.copy(isLoading = true) }

        register(
            s.firstName,
            s.lastName,
            s.email,
            s.phoneNumber,
            s.selectedDepartment!!.id,
            s.password
        )

        updateState { it.copy(isLoading = false) }
        emitSideEffect(RegisterContract.Effect.NavigateToHome)
    }
}

