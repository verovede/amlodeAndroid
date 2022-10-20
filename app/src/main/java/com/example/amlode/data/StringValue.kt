package com.example.amlode.data
import com.google.gson.annotations.SerializedName

data class StringValue (
    @field:SerializedName("type") val type: String,
    @field:SerializedName("value") val value: String
)