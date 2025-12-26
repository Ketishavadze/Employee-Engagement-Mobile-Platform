package com.example.crosscollab.data.remote.api

import com.example.crosscollab.data.remote.dto.BrowseEventsResponse
import com.example.crosscollab.data.remote.dto.CategoryEventsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BrowseApi {

    @GET("categories/{id}/events")
    suspend fun getEventsByCategory(
        @Path("id") categoryId: Int
    ): CategoryEventsResponse
}
