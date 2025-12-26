package com.example.crosscollab.presentation.screen.home


import androidx.lifecycle.viewModelScope
import com.example.crosscollab.domain.repository.HomeRepository
import com.example.crosscollab.presentation.common.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: HomeRepository
) : BaseViewModel<
        HomeContract.State,
        HomeContract.Event,
        HomeContract.Effect
        >(HomeContract.State()) {

    override fun onEvent(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.LoadHome -> loadHome()
            HomeContract.Event.SeeAllUpcoming ->
                emitSideEffect(HomeContract.Effect.NavigateToUpcoming)
            HomeContract.Event.SeeAllTrending ->
                emitSideEffect(HomeContract.Effect.NavigateToTrending)
            is HomeContract.Event.EventClicked ->
                emitSideEffect(HomeContract.Effect.NavigateToEvent(event.id))
            is HomeContract.Event.CategoryClicked ->
                emitSideEffect(HomeContract.Effect.NavigateToCategory(event.id))
        }
    }

    private fun loadHome() = viewModelScope.launch {
        updateState { it.copy(isLoading = true) }

        val upcoming = repo.getUpcomingEvents()
        val categories = repo.getCategories()
        val trending = repo.getTrendingEvents()
        val faq = repo.getFaq()

        updateState {
            it.copy(
                isLoading = false,
                upcomingEvents = upcoming,
                categories = categories,
                trendingEvents = trending,
                faqs = faq
            )
        }
    }
}
