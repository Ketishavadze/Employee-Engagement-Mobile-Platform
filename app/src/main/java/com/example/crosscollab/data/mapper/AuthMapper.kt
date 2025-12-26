package com.example.crosscollab.data.mapper

import com.example.crosscollab.data.remote.dto.AgendaItemDto
import com.example.crosscollab.data.remote.dto.BrowseEventDto
import com.example.crosscollab.data.remote.dto.CategoryEventDto
import com.example.crosscollab.data.remote.dto.DepartmentDto
import com.example.crosscollab.data.remote.dto.EventDetailsDto
import com.example.crosscollab.data.remote.dto.SpeakerDto
import com.example.crosscollab.domain.model.AgendaItem
import com.example.crosscollab.domain.model.BrowseEvent
import com.example.crosscollab.domain.model.CategoryEvent
import com.example.crosscollab.domain.model.Department
import com.example.crosscollab.domain.model.EventDetails
import com.example.crosscollab.domain.model.Speaker


fun DepartmentDto.toDomain() = Department(id, name)

fun EventDetailsDto.toDomain() =
    EventDetails(
        id,
        type,
        title,
        date,
        time,
        location,
        capacity,
        registrationInfo,
        description,
        agenda.map { it.toDomain() },
        speakers.map { it.toDomain() }
    )

fun AgendaItemDto.toDomain() =
    AgendaItem(number, timeTitle, description)

fun SpeakerDto.toDomain() =
    Speaker(id, name, title)

fun CategoryEventDto.toDomain() =
    CategoryEvent(
        id,
        title,
        description,
        date,
        time,
        location,
        spotsLeft,
        isRegistered
    )

fun BrowseEventDto.toDomain() =
    BrowseEvent(
        id,
        month,
        day,
        category,
        status,
        title,
        time,
        location,
        registeredCount,
        spotsLeft,
        waitlistCount
    )


