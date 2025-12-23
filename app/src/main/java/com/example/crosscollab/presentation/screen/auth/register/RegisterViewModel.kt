package com.example.crosscollab.presentation.screen.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterContract.State())
    val state: StateFlow<RegisterContract.State> = _state.asStateFlow()

    private val _effect = Channel<RegisterContract.Effect>()
    val effect = _effect.receiveAsFlow()

    private var otpTimerJob: Job? = null

    fun onEvent(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.OnFirstNameChanged -> updateFirstName(event.firstName)
            is RegisterContract.Event.OnLastNameChanged -> updateLastName(event.lastName)
            is RegisterContract.Event.OnEmailChanged -> updateEmail(event.email)
            is RegisterContract.Event.OnPhoneNumberChanged -> updatePhoneNumber(event.phoneNumber)
            is RegisterContract.Event.OnOtp1Changed -> updateOtp(1, event.digit)
            is RegisterContract.Event.OnOtp2Changed -> updateOtp(2, event.digit)
            is RegisterContract.Event.OnOtp3Changed -> updateOtp(3, event.digit)
            is RegisterContract.Event.OnOtp4Changed -> updateOtp(4, event.digit)
            is RegisterContract.Event.OnOtp5Changed -> updateOtp(5, event.digit)
            is RegisterContract.Event.OnOtp6Changed -> updateOtp(6, event.digit)
            is RegisterContract.Event.OnDepartmentSelected -> updateDepartment(event.department)
            is RegisterContract.Event.OnPasswordChanged -> updatePassword(event.password)
            is RegisterContract.Event.OnConfirmPasswordChanged -> updateConfirmPassword(event.confirmPassword)
            is RegisterContract.Event.OnPolicyAgreementChanged -> updatePolicyAgreement(event.agreed)
            RegisterContract.Event.OnSendOtpClicked -> sendOtp()
            RegisterContract.Event.OnResendOtpClicked -> resendOtp()
            RegisterContract.Event.OnCreateAccountClicked -> createAccount()
            RegisterContract.Event.OnSignInClicked -> navigateToLogin()
            RegisterContract.Event.OnPasswordRequirementsClicked -> showPasswordRequirements()
        }
    }

    private fun updateFirstName(firstName: String) {
        _state.update {
            it.copy(
                firstName = firstName,
                firstNameError = null,
                isCreateAccountEnabled = validateForm(it.copy(firstName = firstName))
            )
        }
    }

    private fun updateLastName(lastName: String) {
        _state.update {
            it.copy(
                lastName = lastName,
                lastNameError = null,
                isCreateAccountEnabled = validateForm(it.copy(lastName = lastName))
            )
        }
    }

    private fun updateEmail(email: String) {
        _state.update {
            it.copy(
                email = email,
                emailError = null,
                isCreateAccountEnabled = validateForm(it.copy(email = email))
            )
        }
    }

    private fun updatePhoneNumber(phoneNumber: String) {
        _state.update {
            it.copy(
                phoneNumber = phoneNumber,
                phoneNumberError = null,
                isCreateAccountEnabled = validateForm(it.copy(phoneNumber = phoneNumber))
            )
        }
    }

    private fun updateOtp(position: Int, digit: String) {
        _state.update { currentState ->
            val newState = when (position) {
                1 -> currentState.copy(otp1 = digit)
                2 -> currentState.copy(otp2 = digit)
                3 -> currentState.copy(otp3 = digit)
                4 -> currentState.copy(otp4 = digit)
                5 -> currentState.copy(otp5 = digit)
                6 -> currentState.copy(otp6 = digit)
                else -> currentState
            }

            // Auto-focus next field
            if (digit.isNotEmpty() && position < 6) {
                viewModelScope.launch {
                    _effect.send(RegisterContract.Effect.FocusOtpField(position + 1))
                }
            }

            newState.copy(
                otpError = null,
                isCreateAccountEnabled = validateForm(newState)
            )
        }
    }

    private fun updateDepartment(department: String) {
        _state.update {
            it.copy(
                department = department,
                departmentError = null,
                isCreateAccountEnabled = validateForm(it.copy(department = department))
            )
        }
    }

    private fun updatePassword(password: String) {
        _state.update {
            it.copy(
                password = password,
                passwordError = null,
                isCreateAccountEnabled = validateForm(it.copy(password = password))
            )
        }
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        _state.update {
            it.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = null,
                isCreateAccountEnabled = validateForm(it.copy(confirmPassword = confirmPassword))
            )
        }
    }

    private fun updatePolicyAgreement(agreed: Boolean) {
        _state.update {
            it.copy(
                agreeToPolicy = agreed,
                isCreateAccountEnabled = validateForm(it.copy(agreeToPolicy = agreed))
            )
        }
    }

    private fun sendOtp() {
        val currentState = _state.value

        // Validate phone number
        val phoneError = validatePhoneNumber(currentState.phoneNumber)
        if (phoneError != null) {
            _state.update { it.copy(phoneNumberError = phoneError) }
            return
        }

        _state.update { it.copy(isSendingOtp = true) }

        viewModelScope.launch {
            try {
                // TODO: Replace with actual OTP API call
                delay(1500)

                _state.update {
                    it.copy(
                        isSendingOtp = false,
                        otpSent = true,
                        canResendOtp = false
                    )
                }

                _effect.send(RegisterContract.Effect.ShowToast("OTP sent successfully"))
                startOtpTimer()
            } catch (e: Exception) {
                _state.update { it.copy(isSendingOtp = false) }
                _effect.send(RegisterContract.Effect.ShowError(e.message ?: "Failed to send OTP"))
            }
        }
    }

    private fun resendOtp() {
        sendOtp()
    }

    private fun startOtpTimer() {
        otpTimerJob?.cancel()
        otpTimerJob = viewModelScope.launch {
            var secondsLeft = 50
            while (secondsLeft > 0) {
                val minutes = secondsLeft / 60
                val seconds = secondsLeft % 60
                _state.update {
                    it.copy(otpExpiryTime = String.format("%02d:%02d", minutes, seconds))
                }
                delay(1000)
                secondsLeft--
            }
            _state.update { it.copy(canResendOtp = true, otpExpiryTime = "00:00") }
        }
    }

    private fun createAccount() {
        val currentState = _state.value

        // Validate all fields
        val firstNameError = validateName(currentState.firstName, "First name")
        val lastNameError = validateName(currentState.lastName, "Last name")
        val emailError = validateEmail(currentState.email)
        val phoneError = validatePhoneNumber(currentState.phoneNumber)
        val otpError = validateOtp(currentState)
        val departmentError = validateDepartment(currentState.department)
        val passwordError = validatePassword(currentState.password)
        val confirmPasswordError = validateConfirmPassword(
            currentState.password,
            currentState.confirmPassword
        )

        if (firstNameError != null || lastNameError != null || emailError != null ||
            phoneError != null || otpError != null || departmentError != null ||
            passwordError != null || confirmPasswordError != null) {
            _state.update {
                it.copy(
                    firstNameError = firstNameError,
                    lastNameError = lastNameError,
                    emailError = emailError,
                    phoneNumberError = phoneError,
                    otpError = otpError,
                    departmentError = departmentError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError
                )
            }
            return
        }

        if (!currentState.agreeToPolicy) {
            viewModelScope.launch {
                _effect.send(RegisterContract.Effect.ShowError("Please agree to the policy"))
            }
            return
        }

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                // TODO: Replace with actual registration API call
                delay(2000)

                _state.update { it.copy(isLoading = false) }
                _effect.send(RegisterContract.Effect.ShowToast("Account created successfully"))
                _effect.send(RegisterContract.Effect.NavigateToHome)
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                _effect.send(RegisterContract.Effect.ShowError(e.message ?: "Registration failed"))
            }
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            _effect.send(RegisterContract.Effect.NavigateToLogin)
        }
    }

    private fun showPasswordRequirements() {
        viewModelScope.launch {
            _effect.send(RegisterContract.Effect.ShowPasswordRequirements)
        }
    }

    // Validation methods
    private fun validateForm(state: RegisterContract.State): Boolean {
        return state.firstName.isNotBlank() &&
                state.lastName.isNotBlank() &&
                validateEmail(state.email) == null &&
                validatePhoneNumber(state.phoneNumber) == null &&
                validateOtp(state) == null &&
                state.department.isNotBlank() &&
                validatePassword(state.password) == null &&
                validateConfirmPassword(state.password, state.confirmPassword) == null &&
                state.agreeToPolicy
    }

    private fun validateName(name: String, fieldName: String): String? {
        return when {
            name.isBlank() -> "$fieldName is required"
            name.length < 2 -> "$fieldName must be at least 2 characters"
            else -> null
        }
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Please enter a valid email address"
            else -> null
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): String? {
        return when {
            phoneNumber.isBlank() -> "Phone number is required"
            phoneNumber.length < 9 -> "Please enter a valid phone number"
            else -> null
        }
    }

    private fun validateOtp(state: RegisterContract.State): String? {
        val otp = "${state.otp1}${state.otp2}${state.otp3}${state.otp4}${state.otp5}${state.otp6}"
        return when {
            otp.length != 6 -> "Please enter complete OTP code"
            else -> null
        }
    }

    private fun validateDepartment(department: String): String? {
        return if (department.isBlank()) "Please select a department" else null
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 8 -> "Password must be at least 8 characters"
            !password.any { it.isUpperCase() } -> "Password must contain at least one uppercase letter"
            !password.any { it.isLowerCase() } -> "Password must contain at least one lowercase letter"
            !password.any { it.isDigit() } -> "Password must contain at least one number"
            else -> null
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isBlank() -> "Please confirm your password"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
    }

    override fun onCleared() {
        super.onCleared()
        otpTimerJob?.cancel()
    }
}