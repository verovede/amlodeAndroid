package com.example.amlode.intefaces

import com.example.amlode.data.DeaResponse
import com.example.amlode.data.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface deaAPI {
    @GET("v2/entities?type=dea")
    fun getDeas(
    ): Call<ArrayList<DeaResponse?>?>?

    @POST("v2/entities")
    fun postDea(@Body Dea: DeaResponse): Call<Void>?

    @POST("v2/entities")
    fun postUser(@Body User: UserResponse): Call<Void>?
}