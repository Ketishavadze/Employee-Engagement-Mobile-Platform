package com.example.crosscollab.data.remote.api

import com.example.crosscollab.data.remote.dto.BrowseEventsResponse
import retrofit2.http.GET

interface BrowseEventsApi {

    @GET("events")
    suspend fun getEvents(): BrowseEventsResponse
}
