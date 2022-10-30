package com.example.amlode.data
import com.google.gson.annotations.SerializedName

data class PatchUser(
    @field:SerializedName("deas") val deas: ArrayValue,
    @field:SerializedName("points") val points: NumberValue,
)
