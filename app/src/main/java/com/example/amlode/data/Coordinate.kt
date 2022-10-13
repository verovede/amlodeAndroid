package com.example.amlode.data

import com.google.gson.annotations.SerializedName

data class Coordinate (
    @field:SerializedName("lat") val latitude: Double,
    @field:SerializedName("long") val longitude: Double
    )
