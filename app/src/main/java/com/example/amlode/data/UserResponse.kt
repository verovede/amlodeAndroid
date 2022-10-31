package com.example.amlode.data
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("active") val active: BooleanValue,
    @field:SerializedName("deas") val deas: ArrayValue,
    @field:SerializedName("fechaNac") val fechaNac: StringValue,
    @field:SerializedName("name") val name: StringValue,
    @field:SerializedName("lastName") val lastName: StringValue,
    @field:SerializedName("points") val points: NumberValue,
)
