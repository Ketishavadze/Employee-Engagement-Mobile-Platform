package com.example.crosscollab.presentation.features

import com.example.crosscollab.domain.model.EventListItem


interface EventListContract {

    data class State(
        val events: List<EventListItem> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val isRefreshing: Boolean = false,
        val hasMorePages: Boolean = true,
        val currentPage: Int = 1
    )

    sealed class Event {
        object LoadEvents : Event()
        object RefreshEvents : Event()
        object LoadMoreEvents : Event()
        data class OnEventClick(val eventId: String) : Event()
        object OnCreateEventClick : Event()
    }

    sealed class Effect {
        data class NavigateToEventDetails(val eventId: String) : Effect()
        object NavigateToCreateEvent : Effect()
        data class ShowError(val message: String) : Effect()
    }
}