package com.example.crosscollab.presentation.screen.browsebycategory

import androidx.lifecycle.viewModelScope
import com.example.crosscollab.domain.repository.BrowseRepository
import com.example.crosscollab.presentation.common.BaseViewModel
import kotlinx.coroutines.launch


class BrowseByCategoryViewModel(
    private val repo: BrowseRepository
) : BaseViewModel<
        BrowseByCategoryContract.State,
        BrowseByCategoryContract.Event,
        BrowseByCategoryContract.Effect
        >(BrowseByCategoryContract.State()) {

    override fun onEvent(event: BrowseByCategoryContract.Event) {
        when (event) {
            is BrowseByCategoryContract.Event.Load ->
                load(event.categoryId, event.title)

            is BrowseByCategoryContract.Event.EventClicked ->
                emitSideEffect(
                    BrowseByCategoryContract.Effect.NavigateToDetails(
                        event.eventId
                    )
                )

            BrowseByCategoryContract.Event.BackClicked ->
                emitSideEffect(BrowseByCategoryContract.Effect.NavigateBack)
        }
    }

    private fun load(categoryId: Int, title: String) =
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, title = title) }

            val events = repo.getEventsByCategory(categoryId)

            updateState {
                it.copy(isLoading = false, events = events)
            }
        }
}
