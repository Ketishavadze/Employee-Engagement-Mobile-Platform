package com.example.crosscollab.presentation.screen.browseevents

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.databinding.FragmentBrowseEventsBinding
import com.example.crosscollab.presentation.common.BaseFragment
import com.google.android.material.chip.Chip

class BrowseEventsFragment :
    BaseFragment<FragmentBrowseEventsBinding>(
        FragmentBrowseEventsBinding::inflate
    ) {

    private val viewModel: BrowseEventsViewModel by viewModels()

    private val adapter = BrowseEventsAdapter {
        viewModel.onEvent(
            BrowseEventsContract.Event.EventClicked(it)
        )
    }

    override fun start() {

        binding.rvEvents.adapter = adapter

        binding.etSearch.addTextChangedListener {
            viewModel.onEvent(
                BrowseEventsContract.Event.SearchChanged(
                    it.toString()
                )
            )
        }

        binding.chipGroupCategories.setOnCheckedStateChangeListener { _, ids ->
            val chip = ids.firstOrNull()?.let {
                binding.root.findViewById<Chip>(it)
            }
            viewModel.onEvent(
                BrowseEventsContract.Event.CategorySelected(
                    chip?.text?.toString() ?: "All"
                )
            )
        }

        viewModel.onEvent(BrowseEventsContract.Event.Load)

        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                adapter.submitList(it.events)
                binding.llEmptyState.visibility =
                    if (it.events.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sideEffect.collect {
                if (it is BrowseEventsContract.Effect.NavigateToDetails) {
                    // navigate to EventDetails
                }
            }
        }
    }
}
