package com.example.crosscollab.data.remote.response

import com.example.crosscollab.data.remote.dto.CategoryDto

data class CategoriesResponse(
    val status: String,
    val data: List<CategoryDto>
)
