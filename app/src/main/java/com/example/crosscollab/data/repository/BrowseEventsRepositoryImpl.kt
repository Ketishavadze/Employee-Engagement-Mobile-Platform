package com.example.crosscollab.data.repository

import com.example.crosscollab.data.mapper.toDomain
import com.example.crosscollab.data.remote.api.BrowseEventsApi
import com.example.crosscollab.domain.model.BrowseEvent
import com.example.crosscollab.domain.repository.BrowseEventsRepository

class BrowseEventsRepositoryImpl(
    private val api: BrowseEventsApi
) : BrowseEventsRepository {

    override suspend fun getEvents(): List<BrowseEvent> =
        api.getEvents().data.map { it.toDomain() }
}
