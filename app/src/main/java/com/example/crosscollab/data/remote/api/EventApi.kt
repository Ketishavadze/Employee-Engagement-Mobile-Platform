package com.example.crosscollab.data.remote.api

import com.example.crosscollab.data.remote.response.EventDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApi {

    @GET("events/{id}")
    suspend fun getEventDetails(
        @Path("id") id: Int
    ): EventDetailsResponse
}
