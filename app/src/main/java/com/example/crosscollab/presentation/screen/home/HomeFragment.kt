package com.example.crosscollab.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crosscollab.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var upcomingEventsAdapter: UpcomingEventsAdapter
    private lateinit var trendingEventsAdapter: TrendingEventsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var faqAdapter: FaqAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupClickListeners()
        observeViewModel()

        // Load data
        viewModel.loadHomeData()
    }

    private fun setupRecyclerViews() {
        // Upcoming Events - Vertical
        upcomingEventsAdapter = UpcomingEventsAdapter { event ->
            // Handle event click
            // Navigate to event details
        }
        binding.rvUpcomingEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = upcomingEventsAdapter
        }

        // Trending Events - Horizontal
        trendingEventsAdapter = TrendingEventsAdapter { event ->
            // Handle event click
            // Navigate to event details
        }
        binding.rvTrendingEvents.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = trendingEventsAdapter
        }

        // Categories - Grid
        categoriesAdapter = CategoriesAdapter { category ->
            // Handle category click
            // Navigate to category events
        }
        binding.rvCategories.apply {
            adapter = categoriesAdapter
        }

        // FAQ
        faqAdapter = FaqAdapter { faq ->
            // Handle FAQ click - expand/collapse
        }
        binding.rvFaq.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = faqAdapter
        }
    }

    private fun setupClickListeners() {
        binding.ivSearch.setOnClickListener {
            // Handle search click
        }

        binding.ivNotification.setOnClickListener {
            // Handle notification click
        }

        binding.tvSeeAllUpcoming.setOnClickListener {
            // Navigate to all upcoming events
        }

        binding.tvSeeAllTrending.setOnClickListener {
            // Navigate to all trending events
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.upcomingEvents.collect { events ->
                upcomingEventsAdapter.submitList(events)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.trendingEvents.collect { events ->
                trendingEventsAdapter.submitList(events)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categories.collect { categories ->
                categoriesAdapter.submitList(categories)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.faqs.collect { faqs ->
                faqAdapter.submitList(faqs)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}