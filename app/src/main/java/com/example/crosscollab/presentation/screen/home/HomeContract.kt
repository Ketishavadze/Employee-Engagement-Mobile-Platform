package com.example.crosscollab.presentation.screen.home

import com.example.crosscollab.domain.model.Category
import com.example.crosscollab.domain.model.Faq
import com.example.crosscollab.domain.model.TrendingEvent
import com.example.crosscollab.domain.model.UpcomingEvent

object HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val upcomingEvents: List<UpcomingEvent> = emptyList(),
        val trendingEvents: List<TrendingEvent> = emptyList(),
        val categories: List<Category> = emptyList(),
        val faqs: List<Faq> = emptyList()
    )

    sealed interface Event {
        object LoadHome : Event
        data class CategoryClicked(val id: Int) : Event
        data class EventClicked(val id: Int) : Event
        object SeeAllUpcoming : Event
        object SeeAllTrending : Event
    }

    sealed interface Effect {
        data class NavigateToEvent(val id: Int) : Effect
        data class NavigateToCategory(val id: Int) : Effect
        object NavigateToUpcoming : Effect
        object NavigateToTrending : Effect
    }
}
