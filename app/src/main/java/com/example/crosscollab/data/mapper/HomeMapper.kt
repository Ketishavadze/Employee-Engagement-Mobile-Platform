package com.example.crosscollab.data.mapper

import com.example.crosscollab.data.remote.dto.CategoryDto
import com.example.crosscollab.data.remote.dto.FaqDto
import com.example.crosscollab.data.remote.dto.TrendingEventDto
import com.example.crosscollab.data.remote.dto.UpcomingEventDto
import com.example.crosscollab.domain.model.Category
import com.example.crosscollab.domain.model.Faq
import com.example.crosscollab.domain.model.TrendingEvent
import com.example.crosscollab.domain.model.UpcomingEvent

fun UpcomingEventDto.toDomain() =
    UpcomingEvent(
        id = id,
        title = title,
        day = day,
        month = month,
        time = time,
        location = location
    )

fun TrendingEventDto.toDomain() =
    TrendingEvent(
        id = id,
        title = title,
        date = date,
        location = location,
        capacity = capacity
    )

fun CategoryDto.toDomain() =
    Category(
        id = id,
        name = name,
        icon = icon,
        count = count
    )

fun FaqDto.toDomain() =
    Faq(
        id = id,
        question = question,
        answer = answer
    )
