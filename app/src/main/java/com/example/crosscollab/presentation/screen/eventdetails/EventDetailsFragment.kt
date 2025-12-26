package com.example.crosscollab.presentation.screen.eventdetails

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.databinding.FragmentEventDetailsBinding
import com.example.crosscollab.presentation.common.BaseFragment

class EventDetailsFragment :
    BaseFragment<FragmentEventDetailsBinding>(
        FragmentEventDetailsBinding::inflate
    ) {

    private val viewModel: EventDetailsViewModel by viewModels()

    private val agendaAdapter = AgendaAdapter()
    private val speakerAdapter = SpeakerAdapter()

    override fun start() {

        val eventId = requireArguments().getInt("eventId")

        binding.rvAgenda.adapter = agendaAdapter
        binding.rvSpeakers.adapter = speakerAdapter

        binding.toolbar.setNavigationOnClickListener {
            viewModel.onEvent(EventDetailsContract.Event.BackClicked)
        }

        binding.btnRegister.setOnClickListener {
            viewModel.onEvent(EventDetailsContract.Event.RegisterClicked)
        }

        viewModel.onEvent(EventDetailsContract.Event.Load(eventId))

        observeState()
        observeEffect()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                state.event?.let { e ->
                    binding.tvEventType.text = e.type
                    binding.tvEventTitle.text = e.title
                    binding.tvEventDate.text = e.date
                    binding.tvEventTime.text = e.time
                    binding.tvEventLocation.text = e.location
                    binding.tvEventCapacity.text = e.capacity
                    binding.tvRegistrationInfo.text = e.registrationInfo
                    binding.tvEventDescription.text = e.description

                    agendaAdapter.submitList(e.agenda)
                    speakerAdapter.submitList(e.speakers)
                }
            }
        }
    }

    private fun observeEffect() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    EventDetailsContract.Effect.NavigateBack ->
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    EventDetailsContract.Effect.ShowRegisteredSuccess -> {
                        // show snackbar / toast
                    }
                }
            }
        }
    }
}
