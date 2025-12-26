package com.example.crosscollab.data.remote.dto

import com.example.crosscollab.data.remote.api.ApiResponse


data class DepartmentsResponse(
    val data: List<DepartmentDto>
)

data class DepartmentDto(
    val id: Int,
    val name: String
)

data class SendOtpRequest(val phoneNumber: String)
data class SendOtpResponse(val data: OtpData)
data class OtpData(val otpExpiresInSeconds: Int)

data class VerifyOtpRequest(
    val phoneNumber: String,
    val otp: String
)

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val departmentId: Int,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class ForgotPasswordRequest(
    val email: String
)
data class UpcomingEventDto(
    val id: Int,
    val title: String,
    val day: String,
    val month: String,
    val time: String,
    val location: String
)
data class TrendingEventDto(
    val id: Int,
    val title: String,
    val date: String,
    val location: String,
    val capacity: String
)
data class CategoryDto(
    val id: Int,
    val name: String,
    val icon: String,
    val count: Int
)
data class FaqDto(
    val id: Int,
    val question: String,
    val answer: String
)
data class EventDetailsDto(
    val id: Int,
    val type: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val capacity: String,
    val registrationInfo: String,
    val description: String,
    val agenda: List<AgendaItemDto>,
    val speakers: List<SpeakerDto>
)

data class AgendaItemDto(
    val number: Int,
    val timeTitle: String,
    val description: String
)

data class SpeakerDto(
    val id: Int,
    val name: String,
    val title: String
)

data class CategoryEventDto(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String,
    val spotsLeft: String,
    val isRegistered: Boolean
)

typealias CategoryEventsResponse =
        ApiResponse<List<CategoryEventDto>>

data class BrowseEventDto(
    val id: Int,
    val month: String,
    val day: String,
    val category: String,
    val status: String,
    val title: String,
    val time: String,
    val location: String,
    val registeredCount: Int,
    val spotsLeft: Int,
    val waitlistCount: Int
)
typealias BrowseEventsResponse =
        ApiResponse<List<BrowseEventDto>>
