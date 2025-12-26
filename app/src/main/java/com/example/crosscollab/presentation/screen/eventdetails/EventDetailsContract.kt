package com.example.crosscollab.presentation.screen.eventdetails

import com.example.crosscollab.domain.model.EventDetails

object EventDetailsContract {

    data class State(
        val isLoading: Boolean = false,
        val event: EventDetails? = null
    )

    sealed interface Event {
        data class Load(val eventId: Int) : Event
        object RegisterClicked : Event
        object BackClicked : Event
    }

    sealed interface Effect {
        object NavigateBack : Effect
        object ShowRegisteredSuccess : Effect
    }
}
