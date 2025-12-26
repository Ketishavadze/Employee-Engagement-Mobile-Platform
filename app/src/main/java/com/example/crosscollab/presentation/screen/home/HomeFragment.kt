package com.example.crosscollab.presentation.screen.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.databinding.FragmentHomeBinding
import com.example.crosscollab.presentation.common.BaseFragment

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val upcomingAdapter = UpcomingEventsAdapter {
        viewModel.onEvent(HomeContract.Event.EventClicked(it))
    }
    private val trendingAdapter = TrendingEventsAdapter {
        viewModel.onEvent(HomeContract.Event.EventClicked(it))
    }
    private val categoryAdapter = CategoryAdapter {
        viewModel.onEvent(HomeContract.Event.CategoryClicked(it))
    }
    private val faqAdapter = FaqAdapter()

    override fun start() {

        binding.rvUpcomingEvents.adapter = upcomingAdapter
        binding.rvTrendingEvents.adapter = trendingAdapter
        binding.rvCategories.adapter = categoryAdapter
        binding.rvFaq.adapter = faqAdapter

        binding.tvSeeAllUpcoming.setOnClickListener {
            viewModel.onEvent(HomeContract.Event.SeeAllUpcoming)
        }

        binding.tvSeeAllTrending.setOnClickListener {
            viewModel.onEvent(HomeContract.Event.SeeAllTrending)
        }

        viewModel.onEvent(HomeContract.Event.LoadHome)

        observeState()
        observeEffect()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                upcomingAdapter.submitList(state.upcomingEvents)
                trendingAdapter.submitList(state.trendingEvents)
                categoryAdapter.submitList(state.categories)
                faqAdapter.submitList(state.faqs)
            }
        }
    }

    private fun observeEffect() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sideEffect.collect {
                // navigation here
            }
        }
    }
}
