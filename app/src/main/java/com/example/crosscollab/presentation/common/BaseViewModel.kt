package com.example.crosscollab.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<State, Event, Effect>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _sideEffect = Channel<Effect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    abstract fun onEvent(event: Event)

    protected fun updateState(reducer: (State) -> State) {
        _state.value = reducer(_state.value)
    }

    protected fun emitSideEffect(effect: Effect) {
        _sideEffect.trySend(effect)
    }
}