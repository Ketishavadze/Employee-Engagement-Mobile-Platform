package com.example.crosscollab.data.remote.response

import com.example.crosscollab.data.remote.dto.UpcomingEventDto

data class UpcomingEventsResponse(
    val status: String,
    val data: List<UpcomingEventDto>
)
