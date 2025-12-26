package com.example.crosscollab.domain.usecase.event


import com.example.crosscollab.domain.repository.EventRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: String): Result<Unit> {
        return eventRepository.deleteEvent(eventId)
    }
}