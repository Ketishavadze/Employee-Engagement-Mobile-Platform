package com.example.crosscollab.domain.usecase.auth


import com.example.crosscollab.domain.repository.AuthRepository

class GetDepartmentsUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke() = repo.getDepartments()
}

class SendOtpUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(phone: String) = repo.sendOtp(phone)
}

class VerifyOtpUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(phone: String, otp: String) =
        repo.verifyOtp(phone, otp)
}

class RegisterUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(
        first: String,
        last: String,
        email: String,
        phone: String,
        departmentId: Int,
        password: String
    ) = repo.register(first, last, email, phone, departmentId, password)
}
class LoginUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repo.login(email, password)
}

class ForgotPasswordUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String) =
        repo.forgotPassword(email)
}
