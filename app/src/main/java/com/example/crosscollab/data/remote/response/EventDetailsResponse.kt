package com.example.crosscollab.data.remote.response


import com.example.crosscollab.data.remote.dto.EventDetailsDto

data class EventDetailsResponse(
    val status: String,
    val data: EventDetailsDto
)
