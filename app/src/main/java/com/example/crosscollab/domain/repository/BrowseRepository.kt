package com.example.crosscollab.domain.repository

import com.example.crosscollab.domain.model.BrowseEvent
import com.example.crosscollab.domain.model.CategoryEvent

interface BrowseRepository {
    suspend fun getEventsByCategory(categoryId: Int): List<CategoryEvent>
}
