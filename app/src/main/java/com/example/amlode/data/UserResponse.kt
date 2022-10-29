package com.example.amlode.data

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("type") val type: String,
    // todo Terminar estructura de datos
)
