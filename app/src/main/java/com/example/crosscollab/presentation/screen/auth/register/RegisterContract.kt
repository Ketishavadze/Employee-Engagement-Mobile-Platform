package com.example.crosscollab.presentation.screen.auth.register

data class RegisterState(
    val isLoading: Boolean = false,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val otpSent: Boolean = false
)

sealed class RegisterEvent {
    data class OnFirstNameChanged(val firstName: String) : RegisterEvent()
    data class OnLastNameChanged(val lastName: String) : RegisterEvent()
    data class OnEmailChanged(val email: String) : RegisterEvent()
    data class OnPhoneChanged(val phone: String) : RegisterEvent()
    data class OnPasswordChanged(val password: String) : RegisterEvent()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent()
    data class OnSendOtpClicked(val phone: String) : RegisterEvent()
    data class OnRegisterClicked(
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String,
        val department: String,
        val password: String,
        val confirmPassword: String,
        val acceptedTerms: Boolean
    ) : RegisterEvent()
    object OnSignInClicked : RegisterEvent()
}

sealed class RegisterSideEffect {
    object NavigateToHome : RegisterSideEffect()
    object NavigateToLogin : RegisterSideEffect()
    data class ShowError(val message: String) : RegisterSideEffect()
    data class ShowSuccess(val message: String) : RegisterSideEffect()
    data class ShowOtpSent(val phone: String) : RegisterSideEffect()
}