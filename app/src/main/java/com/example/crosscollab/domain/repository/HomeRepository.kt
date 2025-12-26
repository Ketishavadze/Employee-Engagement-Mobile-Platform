package com.example.crosscollab.domain.repository

import com.example.crosscollab.domain.model.Category
import com.example.crosscollab.domain.model.Faq
import com.example.crosscollab.domain.model.TrendingEvent
import com.example.crosscollab.domain.model.UpcomingEvent

interface HomeRepository {

    suspend fun getUpcomingEvents(): List<UpcomingEvent>

    suspend fun getTrendingEvents(): List<TrendingEvent>

    suspend fun getCategories(): List<Category>

    suspend fun getFaq(): List<Faq>
}
