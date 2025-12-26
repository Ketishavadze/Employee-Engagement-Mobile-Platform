package com.example.crosscollab.domain.repository

import com.example.crosscollab.domain.model.Department
import com.example.crosscollab.domain.model.LoginRequest
import com.example.crosscollab.domain.model.User
import com.example.crosscollab.domain.util.Result

interface AuthRepository {
    suspend fun getDepartments(): List<Department>
    suspend fun sendOtp(phone: String): Int
    suspend fun verifyOtp(phone: String, otp: String)
    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        departmentId: Int,
        password: String
    )

    suspend fun login(email: String, password: String)
    suspend fun forgotPassword(email: String)
}
