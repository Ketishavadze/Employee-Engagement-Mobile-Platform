package com.example.crosscollab.presentation.screen.browsebycategory

import com.example.crosscollab.domain.model.CategoryEvent


object BrowseByCategoryContract {

    data class State(
        val isLoading: Boolean = false,
        val title: String = "",
        val events: List<CategoryEvent> = emptyList()
    )

    sealed interface Event {
        data class Load(val categoryId: Int, val title: String) : Event
        data class EventClicked(val eventId: Int) : Event
        object BackClicked : Event
    }

    sealed interface Effect {
        object NavigateBack : Effect
        data class NavigateToDetails(val eventId: Int) : Effect
    }
}
