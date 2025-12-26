package com.example.crosscollab.data.remote.api

import com.example.crosscollab.data.remote.response.CategoriesResponse
import com.example.crosscollab.data.remote.response.FaqResponse
import com.example.crosscollab.data.remote.response.TrendingEventsResponse
import com.example.crosscollab.data.remote.response.UpcomingEventsResponse
import retrofit2.http.GET

interface HomeApi {

    @GET("home/upcoming-events")
    suspend fun getUpcomingEvents(): UpcomingEventsResponse

    @GET("home/trending-events")
    suspend fun getTrendingEvents(): TrendingEventsResponse

    @GET("home/categories")
    suspend fun getCategories(): CategoriesResponse

    @GET("home/faq")
    suspend fun getFaq(): FaqResponse
}
