package com.example.amlode.intefaces

import com.example.amlode.data.DeaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

//"v2/entities?type=dea"
interface deaAPI {
    @GET("v2/entities?type=dea")
    fun getDeas(
    ): Call<ArrayList<DeaResponse?>?>?

    @POST("v2/entities?type=dea")
    fun agregarDeas(
    ): Call<ArrayList<DeaResponse?>?>?
}