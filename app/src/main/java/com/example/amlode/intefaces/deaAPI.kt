package com.example.amlode.intefaces

import com.example.amlode.data.DeaResponse
import retrofit2.Call
import retrofit2.http.GET

interface deaAPI {

    @GET(".")
    fun getDeas(
    ): Call<ArrayList<DeaResponse?>?>?

}