package com.example.crosscollab.domain.model


data class AuthResult(
    val token: String,
    val userId: Int,
    val fullName: String,
    val role: String,
    val expiresAt: String
)
