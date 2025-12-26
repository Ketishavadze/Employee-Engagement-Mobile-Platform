package com.example.crosscollab.data.remote.response

import com.example.crosscollab.data.remote.dto.FaqDto

data class FaqResponse(
    val status: String,
    val data: List<FaqDto>
)
