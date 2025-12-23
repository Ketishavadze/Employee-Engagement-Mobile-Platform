package com.example.crosscollab.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _upcomingEvents = MutableStateFlow<List<Event>>(emptyList())
    val upcomingEvents: StateFlow<List<Event>> = _upcomingEvents.asStateFlow()

    private val _trendingEvents = MutableStateFlow<List<Event>>(emptyList())
    val trendingEvents: StateFlow<List<Event>> = _trendingEvents.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _faqs = MutableStateFlow<List<Faq>>(emptyList())
    val faqs: StateFlow<List<Faq>> = _faqs.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true

            // Simulate loading data
            // In a real app, you would fetch from a repository
            _upcomingEvents.value = getDummyUpcomingEvents()
            _trendingEvents.value = getDummyTrendingEvents()
            _categories.value = getDummyCategories()
            _faqs.value = getDummyFaqs()

            _isLoading.value = false
        }
    }

    private fun getDummyUpcomingEvents(): List<Event> {
        return listOf(
            Event(
                id = "1",
                title = "Tech Conference 2025",
                date = "Jan 15, 2025 • 10:00 AM",
                location = "San Francisco, CA",
                imageUrl = "",
                capacity = "Available",
                attendees = 250,
                isFavorite = false
            ),
            Event(
                id = "2",
                title = "Music Festival",
                date = "Jan 20, 2025 • 6:00 PM",
                location = "Los Angeles, CA",
                imageUrl = "",
                capacity = "5 spots left",
                attendees = 500,
                isFavorite = true
            )
        )
    }

    private fun getDummyTrendingEvents(): List<Event> {
        return listOf(
            Event(
                id = "3",
                title = "Art Exhibition",
                date = "Jan 18, 2025 • 2:00 PM",
                location = "New York, NY",
                imageUrl = "",
                capacity = "Available",
                attendees = 150,
                isFavorite = false
            ),
            Event(
                id = "4",
                title = "Food Festival",
                date = "Jan 22, 2025 • 11:00 AM",
                location = "Chicago, IL",
                imageUrl = "",
                capacity = "10 spots left",
                attendees = 300,
                isFavorite = false
            ),
            Event(
                id = "5",
                title = "Sports Tournament",
                date = "Jan 25, 2025 • 9:00 AM",
                location = "Miami, FL",
                imageUrl = "",
                capacity = "Available",
                attendees = 400,
                isFavorite = true
            )
        )
    }

    private fun getDummyCategories(): List<Category> {
        return listOf(
            Category("1", "Team Building", "team_building", 12),
            Category("2", "Sports", "sports", 8),
            Category("3", "Workshops", "workshops", 18),
            Category("4", "Happy Fridays", "happy_fridays", 4),
            Category("5", "Cultural", "cultural", 6),
            Category("6", "Wellness", "wellness", 9)
        )
    }

    private fun getDummyFaqs(): List<Faq> {
        return listOf(
            Faq(
                id = "1",
                question = "How do I register for an event?",
                answer = "Simply click on the event card and tap the 'Register' button. You'll be guided through the registration process.",
                isExpanded = false
            ),
            Faq(
                id = "2",
                question = "Can I get a refund for my ticket?",
                answer = "Refund policies vary by event. Check the event details page for specific refund information.",
                isExpanded = false
            ),
            Faq(
                id = "3",
                question = "How do I contact event organizers?",
                answer = "You can find contact information on the event details page under the 'Organizer' section.",
                isExpanded = false
            )
        )
    }
}

// Data models
data class Event(
    val id: String,
    val title: String,
    val date: String,
    val location: String,
    val imageUrl: String,
    val capacity: String? = null, // e.g., "Available", "10 spots left", "Full"
    val attendees: Int = 0,
    val isFavorite: Boolean = false
)

data class Category(
    val id: String,
    val name: String,
    val iconName: String, // Icon name for mapping to drawable resource
    val eventCount: Int // Number of events in this category
)

data class Faq(
    val id: String,
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false
)