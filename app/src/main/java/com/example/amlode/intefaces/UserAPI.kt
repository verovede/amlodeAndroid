package com.example.amlode.intefaces

import com.example.amlode.data.DeaResponse
import com.example.amlode.data.PatchUser
import com.example.amlode.data.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {
    @POST("v2/entities")
    fun postUser(@Body User: UserResponse): Call<Void>?

    @GET
    fun getUser(@Url url: String): Call<UserResponse?>?

    @PUT
    fun putUser(@Url url: String, @Body Dea: DeaResponse): Call<UserResponse?>?

    @PATCH("v2/entities/{id}/attrs?type=user")
    fun patchUser(@Path("id") id: String?, @Body contacts: PatchUser): Call<Void>?
}