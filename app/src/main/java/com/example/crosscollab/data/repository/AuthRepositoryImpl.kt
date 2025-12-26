package com.example.crosscollab.data.repository

import com.example.crosscollab.data.mapper.toDomain
import com.example.crosscollab.data.remote.api.AuthApi
import com.example.crosscollab.data.remote.dto.ForgotPasswordRequest
import com.example.crosscollab.data.remote.dto.RegisterRequest
import com.example.crosscollab.data.remote.dto.SendOtpRequest
import com.example.crosscollab.data.remote.dto.VerifyOtpRequest
import com.example.crosscollab.domain.model.Department
import com.example.crosscollab.domain.model.LoginRequest
import com.example.crosscollab.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun getDepartments(): List<Department> =
        api.getDepartments().data.map { it.toDomain() }

    override suspend fun sendOtp(phone: String): Int =
        api.sendOtp(SendOtpRequest(phone)).data.otpExpiresInSeconds

    override suspend fun verifyOtp(phone: String, otp: String) {
        api.verifyOtp(VerifyOtpRequest(phone, otp))
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        departmentId: Int,
        password: String
    ) {
        api.register(
            RegisterRequest(
                firstName,
                lastName,
                email,
                phone,
                departmentId,
                password
            )
        )
    }
    override suspend fun login(email: String, password: String) {
        api.login(LoginRequest(email, password))
    }

    override suspend fun forgotPassword(email: String) {
        api.forgotPassword(ForgotPasswordRequest(email))
    }

}
