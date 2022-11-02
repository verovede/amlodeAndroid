package com.example.amlode.intefaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PersistentAPI {
    @Headers("Accept: application/json")
    @POST("v2/subscriptions")
    fun postSubscriptions(@Body Persistent: String): Call<Void>?
}