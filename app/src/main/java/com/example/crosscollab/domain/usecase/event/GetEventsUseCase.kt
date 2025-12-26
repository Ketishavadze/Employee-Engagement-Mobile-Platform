package com.example.crosscollab.domain.usecase.event


import com.example.crosscollab.domain.model.EventListItem
import com.example.crosscollab.domain.model.PagedResult
import com.example.crosscollab.domain.repository.EventRepository
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(
        pageNumber: Int = 1,
        pageSize: Int = 10
    ): Result<PagedResult<EventListItem>> {
        return eventRepository.getEvents(pageNumber, pageSize)
    }
}