package com.example.crosscollab.domain.model

data class Faq(
    val id: Int,
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false
)