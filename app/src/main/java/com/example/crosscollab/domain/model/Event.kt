package com.example.crosscollab.domain.model

data class UpcomingEvent(
    val id: Int,
    val title: String,
    val day: String,
    val month: String,
    val time: String,
    val location: String
)

data class TrendingEvent(
    val id: Int,
    val title: String,
    val date: String,
    val location: String,
    val capacity: String
)

data class Category(
    val id: Int,
    val name: String,
    val icon: String,
    val count: Int
)

data class Faq(
    val id: Int,
    val question: String,
    val answer: String
)
data class EventDetails(
    val id: Int,
    val type: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val capacity: String,
    val registrationInfo: String,
    val description: String,
    val agenda: List<AgendaItem>,
    val speakers: List<Speaker>
)

data class AgendaItem(
    val number: Int,
    val timeTitle: String,
    val description: String
)

data class Speaker(
    val id: Int,
    val name: String,
    val title: String
)

data class CategoryEvent(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String,
    val spotsLeft: String,
    val isRegistered: Boolean
)
data class BrowseEvent(
    val id: Int,
    val month: String,
    val day: String,
    val category: String,
    val status: String,
    val title: String,
    val time: String,
    val location: String,
    val registeredCount: Int,
    val spotsLeft: Int,
    val waitlistCount: Int
)

