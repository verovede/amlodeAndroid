package com.example.amlode.data
import com.google.gson.annotations.SerializedName

data class DeaResponse(
    @field:SerializedName("id") val id: Number,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("active") val active: BooleanValue,
    @field:SerializedName("address") val address: StringValue,
    @field:SerializedName("datestamp") val datestamp: StringValue,
    @field:SerializedName("latitude") val latitude: StringValue,
    @field:SerializedName("longitude") val longitude: StringValue,
)