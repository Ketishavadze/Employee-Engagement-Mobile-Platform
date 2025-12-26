package com.example.crosscollab.data.remote.response

import com.example.crosscollab.data.remote.dto.TrendingEventDto

data class TrendingEventsResponse(
    val status: String,
    val data: List<TrendingEventDto>
)
