package com.example.crosscollab.data.remote.api

data class ApiResponse<T>(
    val status: String,
    val data: T
)
