package com.example.crosscollab.presentation.screen.browsebycategory


import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.databinding.FragmentBrowseByCategoryBinding
import com.example.crosscollab.presentation.common.BaseFragment


class BrowseByCategoryFragment :
    BaseFragment<FragmentBrowseByCategoryBinding>(
        FragmentBrowseByCategoryBinding::inflate
    ) {

    private val viewModel: BrowseByCategoryViewModel by viewModels()

    private val adapter = BrowseEventsAdapter { eventId ->
        viewModel.onEvent(
            BrowseByCategoryContract.Event.EventClicked(eventId)
        )
    }

    override fun start() {

        val categoryId = requireArguments().getInt("categoryId")
        val title = requireArguments().getString("categoryTitle") ?: ""

        binding.rvEvents.adapter = adapter

        binding.btnBack.setOnClickListener {
            viewModel.onEvent(BrowseByCategoryContract.Event.BackClicked)
        }

        viewModel.onEvent(
            BrowseByCategoryContract.Event.Load(categoryId, title)
        )

        observeState()
        observeEffect()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                binding.tvTitle.text = state.title
                adapter.submitList(state.events)
            }
        }
    }

    private fun observeEffect() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    BrowseByCategoryContract.Effect.NavigateBack ->
                        requireActivity()
                            .onBackPressedDispatcher
                            .onBackPressed()

                    is BrowseByCategoryContract.Effect.NavigateToDetails -> {
                        // navigate to EventDetailsFragment
                    }
                }
            }
        }
    }
}
