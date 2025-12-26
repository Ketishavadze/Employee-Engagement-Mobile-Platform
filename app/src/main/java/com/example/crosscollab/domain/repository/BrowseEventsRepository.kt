package com.example.crosscollab.domain.repository

import com.example.crosscollab.domain.model.BrowseEvent

interface BrowseEventsRepository {
    suspend fun getEvents(): List<BrowseEvent>
}
