package com.example.crosscollab.presentation.screen.auth.register


object RegisterContract {


    data class State(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val phoneNumber: String = "",
        val otp1: String = "",
        val otp2: String = "",
        val otp3: String = "",
        val otp4: String = "",
        val otp5: String = "",
        val otp6: String = "",
        val department: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val agreeToPolicy: Boolean = false,
        val isLoading: Boolean = false,
        val isSendingOtp: Boolean = false,
        val otpSent: Boolean = false,
        val otpExpiryTime: String = "00:50",
        val canResendOtp: Boolean = false,
        val firstNameError: String? = null,
        val lastNameError: String? = null,
        val emailError: String? = null,
        val phoneNumberError: String? = null,
        val otpError: String? = null,
        val departmentError: String? = null,
        val passwordError: String? = null,
        val confirmPasswordError: String? = null,
        val isCreateAccountEnabled: Boolean = false
    )


    sealed class Event {
        data class OnFirstNameChanged(val firstName: String) : Event()
        data class OnLastNameChanged(val lastName: String) : Event()
        data class OnEmailChanged(val email: String) : Event()
        data class OnPhoneNumberChanged(val phoneNumber: String) : Event()
        data class OnOtp1Changed(val digit: String) : Event()
        data class OnOtp2Changed(val digit: String) : Event()
        data class OnOtp3Changed(val digit: String) : Event()
        data class OnOtp4Changed(val digit: String) : Event()
        data class OnOtp5Changed(val digit: String) : Event()
        data class OnOtp6Changed(val digit: String) : Event()
        data class OnDepartmentSelected(val department: String) : Event()
        data class OnPasswordChanged(val password: String) : Event()
        data class OnConfirmPasswordChanged(val confirmPassword: String) : Event()
        data class OnPolicyAgreementChanged(val agreed: Boolean) : Event()
        object OnSendOtpClicked : Event()
        object OnResendOtpClicked : Event()
        object OnCreateAccountClicked : Event()
        object OnSignInClicked : Event()
        object OnPasswordRequirementsClicked : Event()
    }

    sealed class Effect {
        object NavigateToLogin : Effect()
        object NavigateToHome : Effect()
        data class ShowError(val message: String) : Effect()
        data class ShowToast(val message: String) : Effect()
        data class FocusOtpField(val fieldIndex: Int) : Effect()
        object ShowPasswordRequirements : Effect()
    }
}