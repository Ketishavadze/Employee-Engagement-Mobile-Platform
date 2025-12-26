package com.example.crosscollab.presentation.screen.browseevents

import androidx.lifecycle.viewModelScope
import com.example.crosscollab.domain.model.BrowseEvent
import com.example.crosscollab.domain.repository.BrowseEventsRepository
import com.example.crosscollab.presentation.common.BaseViewModel
import kotlinx.coroutines.launch

class BrowseEventsViewModel(
    private val repo: BrowseEventsRepository
) : BaseViewModel<
        BrowseEventsContract.State,
        BrowseEventsContract.Event,
        BrowseEventsContract.Effect
        >(BrowseEventsContract.State()) {

    private var allEvents: List<BrowseEvent> = emptyList()

    override fun onEvent(event: BrowseEventsContract.Event) {
        when (event) {
            BrowseEventsContract.Event.Load -> load()
            is BrowseEventsContract.Event.SearchChanged ->
                filter(query = event.value)
            is BrowseEventsContract.Event.CategorySelected ->
                filter(category = event.category)
            is BrowseEventsContract.Event.EventClicked ->
                emitSideEffect(
                    BrowseEventsContract.Effect.NavigateToDetails(event.eventId)
                )
        }
    }

    private fun load() = viewModelScope.launch {
        updateState { it.copy(isLoading = true) }

        allEvents = repo.getEvents()

        updateState {
            it.copy(isLoading = false, events = allEvents)
        }
    }

    private fun filter(
        query: String = state.value.query,
        category: String = state.value.selectedCategory
    ) {
        val filtered = allEvents.filter {
            (category == "All" || it.category == category) &&
                    it.title.contains(query, ignoreCase = true)
        }

        updateState {
            it.copy(
                events = filtered,
                query = query,
                selectedCategory = category
            )
        }
    }
}
