package com.example.amlode.data
import com.google.gson.annotations.SerializedName


data class DeaResponse(
    @field:SerializedName("_id") val id: Number,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("latitude") val latitude: Coordinate,
    @field:SerializedName("longitude") val longitude: Coordinate,
    @field:SerializedName("date") val date: Date,
)