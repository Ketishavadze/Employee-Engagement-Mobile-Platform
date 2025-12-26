package com.example.crosscollab.domain.usecase.event

import com.example.crosscollab.domain.model.Event
import com.example.crosscollab.domain.model.UpdateEventRequest
import com.example.crosscollab.domain.repository.EventRepository
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: String, request: UpdateEventRequest): Result<Event> {
        return eventRepository.updateEvent(eventId, request)
    }
}