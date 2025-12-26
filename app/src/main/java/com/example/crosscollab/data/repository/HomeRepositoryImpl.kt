package com.example.crosscollab.data.repository

import com.example.crosscollab.data.mapper.toDomain
import com.example.crosscollab.data.remote.api.HomeApi
import com.example.crosscollab.domain.model.Category
import com.example.crosscollab.domain.model.Faq
import com.example.crosscollab.domain.model.TrendingEvent
import com.example.crosscollab.domain.model.UpcomingEvent
import com.example.crosscollab.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val api: HomeApi
) : HomeRepository {

    override suspend fun getUpcomingEvents(): List<UpcomingEvent> =
        api.getUpcomingEvents().data.map { it.toDomain() }

    override suspend fun getTrendingEvents(): List<TrendingEvent> =
        api.getTrendingEvents().data.map { it.toDomain() }

    override suspend fun getCategories(): List<Category> =
        api.getCategories().data.map { it.toDomain() }

    override suspend fun getFaq(): List<Faq> =
        api.getFaq().data.map { it.toDomain() }
}
