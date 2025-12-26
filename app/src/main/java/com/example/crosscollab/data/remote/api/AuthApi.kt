package com.example.crosscollab.data.remote.api

import com.example.crosscollab.data.remote.dto.DepartmentsResponse
import com.example.crosscollab.data.remote.dto.ForgotPasswordRequest
import com.example.crosscollab.data.remote.dto.LoginRequest
import com.example.crosscollab.data.remote.dto.RegisterRequest
import com.example.crosscollab.data.remote.dto.SendOtpRequest
import com.example.crosscollab.data.remote.dto.SendOtpResponse
import com.example.crosscollab.data.remote.dto.VerifyOtpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @GET("departments")
    suspend fun getDepartments(): DepartmentsResponse

    @POST("auth/send-otp")
    suspend fun sendOtp(@Body body: SendOtpRequest): SendOtpResponse

    @POST("auth/verify_otp")
    suspend fun verifyOtp(@Body body: VerifyOtpRequest)

    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequest)

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest)

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body body: ForgotPasswordRequest)
}

