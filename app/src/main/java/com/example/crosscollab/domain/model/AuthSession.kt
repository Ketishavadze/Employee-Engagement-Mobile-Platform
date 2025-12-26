package com.example.crosscollab.domain.model

import java.time.Instant

data class AuthSession(
    val token: String,
    val userId: Long,
    val fullName: String,
    val role: String,
    val expiresAt: Instant
)
