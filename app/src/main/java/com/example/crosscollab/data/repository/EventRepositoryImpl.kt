package com.example.crosscollab.data.repository

import com.example.crosscollab.data.mapper.toDomain
import com.example.crosscollab.data.remote.api.EventApi
import com.example.crosscollab.domain.model.EventDetails
import com.example.crosscollab.domain.repository.EventRepository

class EventRepositoryImpl(
    private val api: EventApi
) : EventRepository {

    override suspend fun getEventDetails(eventId: Int): EventDetails =
        api.getEventDetails(eventId).data.toDomain()
}
