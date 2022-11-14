package com.example.amlode.intefaces

import com.example.amlode.data.RouteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RoutesAPI {
    @GET("/v2/directions/driving-car")
    suspend fun getRouteByCar(
        @Query("api_key") key: String,
        @Query("start", encoded = true) start: String,
        @Query("end", encoded = true) end: String
    ) : Response<RouteResponse>
}