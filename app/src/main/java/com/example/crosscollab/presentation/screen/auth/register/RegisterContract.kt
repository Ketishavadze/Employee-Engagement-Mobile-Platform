package com.example.crosscollab.presentation.screen.auth.register

import com.example.crosscollab.domain.model.Department


object RegisterContract {

    data class State(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val phoneNumber: String = "",
        val otp: String = "",
        val departments: List<Department> = emptyList(),
        val selectedDepartment: Department? = null,
        val password: String = "",
        val confirmPassword: String = "",
        val agreePolicy: Boolean = false,

        val isLoading: Boolean = false,
        val isSendingOtp: Boolean = false,
        val isOtpVerified: Boolean = false,
        val otpExpirySeconds: Int = 0,

        val error: String? = null
    )

    sealed interface Event {
        data class FirstNameChanged(val value: String) : Event
        data class LastNameChanged(val value: String) : Event
        data class EmailChanged(val value: String) : Event
        data class PhoneChanged(val value: String) : Event
        data class OtpChanged(val value: String) : Event
        data class DepartmentSelected(val department: Department) : Event
        data class PasswordChanged(val value: String) : Event
        data class ConfirmPasswordChanged(val value: String) : Event
        data class PolicyChecked(val checked: Boolean) : Event

        object LoadDepartments : Event
        object SendOtp : Event
        object VerifyOtp : Event
        object CreateAccount : Event
        object SignInClicked : Event
    }

    sealed interface Effect {
        object NavigateToHome : Effect
        object NavigateToSignIn : Effect
        data class ShowToast(val message: String) : Effect
    }
}
