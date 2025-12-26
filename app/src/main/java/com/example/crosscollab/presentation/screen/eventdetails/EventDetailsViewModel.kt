package com.example.crosscollab.presentation.screen.eventdetails

import androidx.lifecycle.viewModelScope
import com.example.crosscollab.domain.repository.EventRepository
import com.example.crosscollab.presentation.common.BaseViewModel
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    private val repo: EventRepository
) : BaseViewModel<
        EventDetailsContract.State,
        EventDetailsContract.Event,
        EventDetailsContract.Effect
        >(EventDetailsContract.State()) {

    override fun onEvent(event: EventDetailsContract.Event) {
        when (event) {
            is EventDetailsContract.Event.Load ->
                loadEvent(event.eventId)
            EventDetailsContract.Event.RegisterClicked ->
                emitSideEffect(EventDetailsContract.Effect.ShowRegisteredSuccess)
            EventDetailsContract.Event.BackClicked ->
                emitSideEffect(EventDetailsContract.Effect.NavigateBack)
        }
    }

    private fun loadEvent(id: Int) = viewModelScope.launch {
        updateState { it.copy(isLoading = true) }

        val details = repo.getEventDetails(id)

        updateState {
            it.copy(isLoading = false, event = details)
        }
    }
}
