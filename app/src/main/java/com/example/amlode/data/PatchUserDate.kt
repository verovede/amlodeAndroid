package com.example.amlode.data

import com.google.gson.annotations.SerializedName

data class PatchUserDate(
    @field:SerializedName("fechaNac") val date: StringValue
)
