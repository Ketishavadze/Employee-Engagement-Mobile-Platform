package com.example.crosscollab.data.repository

import com.example.crosscollab.data.mapper.toDomain
import com.example.crosscollab.data.remote.api.BrowseApi
import com.example.crosscollab.domain.model.BrowseEvent
import com.example.crosscollab.domain.repository.BrowseRepository

class BrowseRepositoryImpl(
    private val api: BrowseApi
) : BrowseRepository {

    override suspend fun getEventsByCategory(categoryId: Int): List<BrowseEvent> =
        api.getEventsByCategory(categoryId).data.map { it.toDomain() }
}
