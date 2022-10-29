package com.example.amlode.data
import com.google.gson.annotations.SerializedName

data class NumberValue(
    @field:SerializedName("type") val type: String,
    @field:SerializedName("value") val value: Number
)
