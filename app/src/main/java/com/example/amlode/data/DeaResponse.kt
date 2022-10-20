package com.example.amlode.data
import com.google.gson.annotations.SerializedName


data class DeaResponse(
    @field:SerializedName("id") val id: Number,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("coordinates") val coordinates: Coordinate,
    @field:SerializedName("owner") val owner: StringValue,
    @field:SerializedName("device") val device: StringValue,
    @field:SerializedName("info") val info: StringValue,
    @field:SerializedName("declared") val declared: StringValue,
    @field:SerializedName("confirmed") val confirmed: StringValue,
    @field:SerializedName("mustConfirm") val mustConfirm: BooleanValue,
)