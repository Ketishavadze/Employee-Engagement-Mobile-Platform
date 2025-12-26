package com.example.crosscollab.domain.usecase.event


import com.example.crosscollab.domain.model.Event
import com.example.crosscollab.domain.repository.EventRepository
import javax.inject.Inject

class GetEventDetailsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: String): Result<Event> {
        return eventRepository.getEventById(eventId)
    }
}