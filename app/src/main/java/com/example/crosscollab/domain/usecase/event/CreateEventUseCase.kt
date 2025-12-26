package com.example.crosscollab.domain.usecase.event


import com.example.crosscollab.domain.model.CreateEventRequest
import com.example.crosscollab.domain.model.Event
import com.example.crosscollab.domain.repository.EventRepository
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(request: CreateEventRequest): Result<Event> {
        return eventRepository.createEvent(request)
    }
}