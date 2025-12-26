package com.example.crosscollab.domain.repository

import com.example.crosscollab.domain.model.EventDetails


interface EventRepository {
    suspend fun getEventDetails(eventId: Int): EventDetails
}
