package com.example.crosscollab.presentation.screen.browseevents

import com.example.crosscollab.domain.model.BrowseEvent

object BrowseEventsContract {

    data class State(
        val isLoading: Boolean = false,
        val events: List<BrowseEvent> = emptyList(),
        val selectedCategory: String = "All",
        val query: String = ""
    )

    sealed interface Event {
        object Load : Event
        data class SearchChanged(val value: String) : Event
        data class CategorySelected(val category: String) : Event
        data class EventClicked(val eventId: Int) : Event
    }

    sealed interface Effect {
        data class NavigateToDetails(val eventId: Int) : Effect
    }
}
