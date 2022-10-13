package com.example.amlode.data
import com.google.gson.annotations.SerializedName


data class DeaResponse(
    @field:SerializedName("_id") val id: Number,
    @field:SerializedName("coord") val coordinate: Coordinate,
)