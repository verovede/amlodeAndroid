package com.example.amlode.data

import com.google.gson.annotations.SerializedName

data class Date(
    @field:SerializedName("type") val type: String,
    @field:SerializedName("value") val value: String
)