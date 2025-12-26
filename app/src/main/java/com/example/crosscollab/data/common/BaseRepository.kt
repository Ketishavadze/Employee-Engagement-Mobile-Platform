package com.example.crosscollab.data.common


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    protected suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): T = withContext(Dispatchers.IO) {
        apiCall()
    }
}
